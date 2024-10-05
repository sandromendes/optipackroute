package com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codejukebox.optipackroute.core.algorithms.binpacking.interfaces.IAnxietatemAlgorithm;
import com.codejukebox.optipackroute.domain.models.binpacking.AnxietatemResultDTO;
import com.codejukebox.optipackroute.domain.models.binpacking.Box;
import com.codejukebox.optipackroute.domain.models.binpacking.Coordinates;
import com.codejukebox.optipackroute.domain.models.binpacking.CornerPoint;
import com.codejukebox.optipackroute.domain.models.binpacking.Dimensions;
import com.codejukebox.optipackroute.persistence.repository.interfaces.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The Packer class manages the packing of boxes into a container, considering
 * the selection of boxes and corner points, and generates new corner points
 * based on the packed boxes.
 */
@Service
public class AnxietatemAlgorithm implements IAnxietatemAlgorithm{

	private static final Logger logger = LoggerFactory.getLogger(AnxietatemAlgorithm.class);

    private final RedisRepository redisRepository;
    private final ObjectMapper objectMapper;
    
	private int state = 1; // Current state of the packer
	private ArrayList<CornerPoint> usedCornerPoints; // Set of points already used and in sequence
	private ArrayList<Box> packedBoxes; // Set of packed boxes
	private ArrayList<Box> unpackedBoxes; // Set of available boxes
	private Box selectedBox; // Currently selected box
	private int numberOfBoxes;
	private Dimensions dimensions;
	
	private static String AVAILABLE_POINTS_DATA_LIST_NAME = "availableCornerPoints";

	/**
	 * Constructor for the Packer class.
	 */
	public AnxietatemAlgorithm(RedisRepository redisRepository, 
			ObjectMapper objectMapper) {
		
		this.redisRepository = redisRepository;
		this.objectMapper = objectMapper;
		
		packedBoxes = new ArrayList<Box>();
		
        var now = LocalDateTime.now();
        var formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        var formattedDateTime = now.format(formatter);
		
		AVAILABLE_POINTS_DATA_LIST_NAME += formattedDateTime;
		
        redisRepository.saveArrayList(AVAILABLE_POINTS_DATA_LIST_NAME, new ArrayList<CornerPoint>());
		
		usedCornerPoints = new ArrayList<CornerPoint>();
	}

	public void setup(Dimensions dimensions, int numberOfBoxes) {		
		this.numberOfBoxes = numberOfBoxes;
		this.dimensions = dimensions;
	}
	
	/**
	 * Randomly selects a box from the set of unpacked boxes and adds it to the set
	 * of packed boxes.
	 * 
	 * @return The randomly selected box.
	 * 
	 * @example
	 * 
	 *          <pre>
	 *          Packer packer = new Packer();
	 *          Box box = packer.chooseBox();
	 *          System.out.println("Chosen box: " + box.getID());
	 *          </pre>
	 */
	public Box chooseBox() {
		if (unpackedBoxes.isEmpty()) {
			logger.error("No boxes available to choose from.");
			throw new IllegalStateException("No unpacked boxes available.");
		}

		Box box = null;
		var random = new Random();

		try {
			var randomIndex = random.nextInt(unpackedBoxes.size());
			box = unpackedBoxes.get(randomIndex);

			packedBoxes.add(box);
			unpackedBoxes.remove(randomIndex);

			logger.info("Box {} chosen", box.getID());
		} catch (IndexOutOfBoundsException e) {
			logger.error("Index out of bounds while choosing a box: {}", e.getMessage());
			throw new IllegalStateException("Error while choosing a box.", e);
		}

		return box;
	}

	/**
	 * Randomly selects a corner point from the set of available corner points and
	 * adds it to the set of used corner points.
	 * 
	 * @return The randomly selected corner point.
	 * 
	 * @example
	 * 
	 *          <pre>
	 *          Packer packer = new Packer();
	 *          CornerPoint point = packer.choosePoint();
	 *          System.out.println("Chosen corner point: " + point.getCornerPoint());
	 *          </pre>
	 */
	public CornerPoint choosePoint() {
		if (retrieveAvailableCornerPoints().isEmpty()) {
			logger.error("No available corner points to choose from.");
			throw new IllegalStateException("No available corner points.");
		}

		CornerPoint cornerPoint = null;
		var random = new Random();

		try {
			int randomIndex = random.nextInt(retrieveAvailableCornerPoints().size());
			logger.info("Chosen random number: {}", randomIndex);

			cornerPoint = retrieveAvailableCornerPoints().get(randomIndex);
			usedCornerPoints.add(cornerPoint);
			
			var item = retrieveAvailableCornerPoints().get(randomIndex);
			
			redisRepository.removeItemFromList(AVAILABLE_POINTS_DATA_LIST_NAME, item);

		} catch (IndexOutOfBoundsException e) {
			logger.error("Index out of bounds while choosing a corner point: {}", e.getMessage());
			throw new IllegalStateException("Error while choosing a corner point.", e);
		}

		return cornerPoint;
	}

	/**
	 * Fills the set of unpacked boxes with randomly generated boxes.
	 * 
	 * @example
	 * 
	 *          <pre>
	 *          Packer packer = new Packer();
	 *          packer.loadRandomBoxArray();
	 *          </pre>
	 */
	public void loadRandomBoxArray(int boxCount) {
		if (boxCount <= 0) {
			logger.error("Invalid box count: {}. Box count must be a positive integer.", boxCount);
			throw new IllegalArgumentException("Box count must be a positive integer.");
		}

		unpackedBoxes = new ArrayList<Box>();

		var random = new Random();

		int width, length, height;

		try {
			for (int i = 0; i < boxCount; i++) {
				var box = new Box();
				box.setID(i);

				width = random.nextInt(10) + 1;
				length = random.nextInt(10) + 1;
				height = random.nextInt(10) + 1;
				var dimensions = new Dimensions(width, length, height);
				box.setDimensions(dimensions);

				unpackedBoxes.add(box);
			}

			logger.info("Loaded {} random boxes", boxCount);

		} catch (Exception e) {
			logger.error("Error loading random boxes: {}", e.getMessage());
			throw new RuntimeException("Failed to load random boxes.", e);
		}
	}

	/**
	 * Packs boxes into a container using an initial corner point. The process
	 * considers the random selection of boxes and corner points, and avoids
	 * exceeding the container's limits.
	 * 
	 * @param container     The container where the boxes will be packed.
	 * @param cornerPoint   The initial corner point for packing.
	 * @param numberOfBoxes The number of boxes to be loaded.
	 * 
	 * @example
	 * 
	 *          <pre>
	 *          Packer packer = new Packer();
	 *          Box container = new Box();
	 *          CornerPoint initialPoint = new CornerPoint(new Coordinates(0, 0, 0));
	 *          packer.pack(container, initialPoint, 100);
	 *          </pre>
	 */
	public AnxietatemResultDTO pack() {

	    var container = new Box();
	    container.setDimensions(dimensions);
	    
	    var coordinates = new Coordinates(0, 0, 0);
	    
	    var cornerPoint = new CornerPoint();
	    cornerPoint.setCoordinates(coordinates);
		
	    if (container == null || cornerPoint == null) {
	        logger.error("Container or corner point is null.");
	        throw new IllegalArgumentException("Container and corner point must not be null.");
	    }

	    int width = container.getDimensions().getWidth();
	    int height = container.getDimensions().getHeight();
	    int length = container.getDimensions().getLength();
	    
	    try {
	        loadRandomBoxArray(numberOfBoxes);

	        usedCornerPoints.add(cornerPoint);
	        
	        redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPoint);

	        logger.info("Box array size before Loop: {}", unpackedBoxes.size());

	        for (int i = 0; unpackedBoxes.size() > 0; i++) {
	            logger.info("Box array size is: {} in iteration number: {}", retrieveAvailableCornerPoints().size(), i);

	            if (state == 1) {
	                selectedBox = chooseBox();
	                cornerPoint = choosePoint();
	                logger.info("Corner with coordinates: {}, {}, {} CHOSEN. Condition st = 1",
	                        cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY(),
	                        cornerPoint.getCoordinates().getZ());
	            } else if (state == 0) {
	                selectedBox = chooseBox();
	                logger.info("Corner with coordinates: {}, {}, {} CHOSEN. Condition st = 0",
	                        cornerPoint.getCoordinates().getX(), cornerPoint.getCoordinates().getY(),
	                        cornerPoint.getCoordinates().getZ());
	            }

	            var cornerCoordinates = new Coordinates(cornerPoint.getCoordinates().getX(),
	                    cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ());

	            var boxXCoordinate = new Coordinates(width, 0, 0);
	            var boxYCoordinate = new Coordinates(0, height, 0);
	            var boxZCoordinate = new Coordinates(0, 0, length);

	            if ((calculateDistanceBetweenPoints(cornerCoordinates, boxXCoordinate) > selectedBox.getDimensions().getWidth())
	                    && (calculateDistanceBetweenPoints(cornerCoordinates, boxYCoordinate) > selectedBox.getDimensions().getHeight())
	                    && (calculateDistanceBetweenPoints(cornerCoordinates, boxZCoordinate) > selectedBox.getDimensions().getLength())) {
	                cornerPoint.setBox(selectedBox);
	                generateCornerPoint(cornerPoint, selectedBox);
	                state = 1;
	            } else {
	                state = 0;
	            }

	            var runtime = Runtime.getRuntime();
	            runtime.gc();
	        }

	        logger.info("Number of used points: {}", usedCornerPoints.size());
	        logger.info("Number of packed boxes: {}", packedBoxes.size());

	    } catch (Exception e) {
	        logger.error("Error during packing process: {}", e.getMessage());
	        throw new RuntimeException("Packing process failed.", e);
	    }

	    // Cria e retorna o PackingResultDTO
	    return new AnxietatemResultDTO(usedCornerPoints, packedBoxes.size(), length, height, width);
	}


	/**
	 * Generates new corner points based on the dimensions of the box and the
	 * provided coordinates.
	 * 
	 * @param cornerPoint The corner point to be used.
	 * @param box         The box that defines the dimensions for calculating the
	 *                    new corner points.
	 */
	public void generateCornerPoint(CornerPoint cornerPoint, Box box) {
		if (cornerPoint == null || box == null) {
			logger.error("Corner point or box is null.");
			throw new IllegalArgumentException("Corner point and box must not be null.");
		}

		Coordinates coordinate;
		List<CornerPoint> points = new ArrayList<CornerPoint>();

		try {
			if ((cornerPoint.getCoordinates().getX() == 0) && (cornerPoint.getCoordinates().getY() == 0)
					&& (cornerPoint.getCoordinates().getZ() == 0)) {
				// Create new corner points

				coordinate = new Coordinates(box.getDimensions().getWidth(), 0, 0);
				var cornerPointByWidth = new CornerPoint(coordinate);
				cornerPointByWidth.setBox(box);

				logger.info("Point with coordinates: {}, {}, {} GENERATED", cornerPointByWidth.getCoordinates().getX(),
						cornerPointByWidth.getCoordinates().getY(), cornerPointByWidth.getCoordinates().getZ());

				redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByWidth);
				points.add(cornerPointByWidth);

				coordinate = new Coordinates(0, box.getDimensions().getHeight(), 0);
				var cornerPointByHeight = new CornerPoint(coordinate);
				cornerPointByHeight.setBox(box);

				logger.info("Point with coordinates: {}, {}, {} GENERATED", cornerPointByHeight.getCoordinates().getX(),
						cornerPointByHeight.getCoordinates().getY(), cornerPointByHeight.getCoordinates().getZ());

				redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByHeight);
				points.add(cornerPointByHeight);

				coordinate = new Coordinates(0, 0, box.getDimensions().getLength());
				var cornerPointByLength = new CornerPoint(coordinate);
				cornerPointByLength.setBox(box);

				logger.info("Point with coordinates: {}, {}, {} GENERATED", cornerPointByLength.getCoordinates().getX(),
						cornerPointByLength.getCoordinates().getY(), cornerPointByLength.getCoordinates().getZ());

				redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByLength);
				points.add(cornerPointByLength);
			} else {

				if (cornerPoint.getBox().getDimensions().getWidth() < box.getDimensions().getWidth()) {
					coordinate = new Coordinates(
							cornerPoint.getCoordinates().getX() + cornerPoint.getBox().getDimensions().getWidth(),
							cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ());

					var cornerPointByWidth = new CornerPoint(coordinate);
					cornerPointByWidth.setBox(box);

					logger.info("Point with coordinates: {}, {}, {} GENERATED",
							cornerPointByWidth.getCoordinates().getX(), cornerPointByWidth.getCoordinates().getY(),
							cornerPointByWidth.getCoordinates().getZ());
					
					redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByWidth);
					points.add(cornerPointByWidth);
				} else {
					coordinate = new Coordinates(cornerPoint.getCoordinates().getX() + box.getDimensions().getWidth(),
							cornerPoint.getCoordinates().getY(), cornerPoint.getCoordinates().getZ());

					var cornerPointByWidth = new CornerPoint(coordinate);
					cornerPointByWidth.setBox(box);

					logger.info("Point with coordinates: {}, {}, {} GENERATED",
							cornerPointByWidth.getCoordinates().getX(), cornerPointByWidth.getCoordinates().getY(),
							cornerPointByWidth.getCoordinates().getZ());

					redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByWidth);
					points.add(cornerPointByWidth);
				}

				if (cornerPoint.getBox().getDimensions().getLength() < box.getDimensions().getLength()) {
					coordinate = new Coordinates(cornerPoint.getCoordinates().getX(),
							cornerPoint.getCoordinates().getY(),
							cornerPoint.getCoordinates().getZ() + cornerPoint.getBox().getDimensions().getLength());

					var cornerPointByLength = new CornerPoint(coordinate);
					cornerPointByLength.setBox(box);

					logger.info("Point with coordinates: {}, {}, {} GENERATED",
							cornerPointByLength.getCoordinates().getX(), cornerPointByLength.getCoordinates().getY(),
							cornerPointByLength.getCoordinates().getZ());

					redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByLength);
					points.add(cornerPointByLength);
				} else {
					coordinate = new Coordinates(cornerPoint.getCoordinates().getX(),
							cornerPoint.getCoordinates().getY(),
							cornerPoint.getCoordinates().getZ() + box.getDimensions().getLength());

					var cornerPointByLength = new CornerPoint(coordinate);
					cornerPointByLength.setBox(box);

					logger.info("Point with coordinates: {}, {}, {} GENERATED",
							cornerPointByLength.getCoordinates().getX(), cornerPointByLength.getCoordinates().getY(),
							cornerPointByLength.getCoordinates().getZ());

					redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByLength);
					points.add(cornerPointByLength);
				}

				if (cornerPoint.getBox().getDimensions().getHeight() < box.getDimensions().getHeight()) {
					coordinate = new Coordinates(cornerPoint.getCoordinates().getX(),
							cornerPoint.getCoordinates().getY() + cornerPoint.getBox().getDimensions().getHeight(),
							cornerPoint.getCoordinates().getZ());

					var cornerPointByHeight = new CornerPoint(coordinate);
					cornerPointByHeight.setBox(box);

					logger.info("Point with coordinates: {}, {}, {} GENERATED",
							cornerPointByHeight.getCoordinates().getX(), cornerPointByHeight.getCoordinates().getY(),
							cornerPointByHeight.getCoordinates().getZ());

					redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByHeight);
					points.add(cornerPointByHeight);
				} else {
					coordinate = new Coordinates(cornerPoint.getCoordinates().getX(),
							cornerPoint.getCoordinates().getY() + box.getDimensions().getHeight(),
							cornerPoint.getCoordinates().getZ());

					var cornerPointByHeight = new CornerPoint(coordinate);
					cornerPointByHeight.setBox(box);

					logger.info("Point with coordinates: {}, {}, {} GENERATED",
							cornerPointByHeight.getCoordinates().getX(), cornerPointByHeight.getCoordinates().getY(),
							cornerPointByHeight.getCoordinates().getZ());

					redisRepository.addToList(AVAILABLE_POINTS_DATA_LIST_NAME, cornerPointByHeight);
					points.add(cornerPointByHeight);
				}
			}
		} catch (Exception e) {
			logger.error("Error generating corner points: {}", e.getMessage());
			throw new RuntimeException("Failed to generate corner points.", e);
		}

		logger.info("Size of available points array before garbage: {}", retrieveAvailableCornerPoints().size());

		var removedPoints = GarbagePoint.collect(points, retrieveAvailableCornerPoints());

		for (CornerPoint removedPoint : removedPoints) {
			redisRepository.removeItemFromList(AVAILABLE_POINTS_DATA_LIST_NAME, removedPoint);
		}
		
		logger.info("Size of available points array after garbage: {}", retrieveAvailableCornerPoints().size());
	}
	
    private List<CornerPoint> retrieveAvailableCornerPoints() {
        var rawData = redisRepository.retrieveArrayList(AVAILABLE_POINTS_DATA_LIST_NAME, CornerPoint.class);
        
        List<CornerPoint> cornerPoints = new ArrayList<>();
        
        for (var item : rawData) {
            try {
            	CornerPoint cornerPoint = objectMapper.convertValue(item, CornerPoint.class);
                cornerPoints.add(cornerPoint);
            } catch (IllegalArgumentException e) {
                logger.info("Failed to convert item to CornerPoint: " + e.getMessage());
            }
        }

        return cornerPoints;
    }
	
	/**
	 * Calculates the distance between two points in 3D space.
	 * 
	 * @param p1 The first point.
	 * @param p2 The second point.
	 * @return The Euclidean distance between the two points.
	 */
	public double calculateDistanceBetweenPoints(Coordinates p1, Coordinates p2) {
		return Math.sqrt(Math.pow((p1.getX() - p2.getX()), 2) + Math.pow((p1.getY() - p2.getY()), 2)
				+ Math.pow((p1.getZ() - p2.getZ()), 2));
	}

	@Override
	public void loadAvailableBoxes(List<Box> boxes) {
		this.unpackedBoxes = (ArrayList<Box>)boxes;
	}

    public void displayPackedBoxes() {
        for (var box : packedBoxes) {
        	logger.info("Packed Box: " + box);
        }
    }
}
