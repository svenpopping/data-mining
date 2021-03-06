import java.util.ArrayList;
import java.util.List;

public class KMeans {
	/**
	 * The number of clusters to detect.
	 */
	private int k;
	
	/**
	 * A collection of (k) clusters.
	 */
	private List<Cluster> clusters;
	
	/**
	 * The unclustered data.
	 */
	private Cluster data;

	/**
	 * Constructor.
	 * @param k The number of clusters to detect.
	 * @param fileName The filename which holds the cluster data.
	 */
	public KMeans(int k, String fileName) {
		this.k = k;
		clusters = new ArrayList<Cluster>();
		data = new Cluster();

		readData(fileName);
	}

	/**
	 * @param i The index of the cluster that is to be retrieved.
	 * @return The cluster at index i.
	 */
	public Cluster getCluster(int i) {
		return clusters.get(i);
	}
	
	/**
	 * @return The unclustered data.
	 */
	public Cluster getData() {
		return data;
	}
	
	/**
	 * @return The collection of clusters.
	 */
	public List<Cluster> getClusters() {
		return clusters;
	}
	
	/**
	 * @return The number of clusters.
	 */
	public int getClusterSize() {
		return clusters.size();
	}

	/**
	 * Reads in the data of filename.
	 * @param fileName The file which is to be read.
	 */
	private void readData(String fileName) {
		data.readData(fileName);
	}

	/**
	 * Adds a new init point at a random location in the dataset.
	 */
	private void addInitPoint() {
        int random = (int) (Math.random() * data.size());
        Cluster newCluster = new Cluster();
        newCluster.add(this.data.get(random));

        this.clusters.add(newCluster);
	}
	
	/**
	 * Clears all elements from the clusters.
	 */
	private void clearClusters() {
		for (Cluster c: clusters) {
			c.clear();
		}
	}

	/**
	 * Computes clusters based on the centroids of the clusters in the previous round.
	 * If no such clusters exist yet, it will select k random points.
	 */
	public void update() {
		// add code here
        if (this.getClusterSize() == 0) {
            for (int i = 0; i < k; i++) {
                this.addInitPoint();
            }
        }

        List<FeatureVector> centroids = new ArrayList<FeatureVector>();
        for (Cluster cluster : clusters) {
            if(cluster.centroid() != null)
                centroids.add(cluster.centroid());
        }

        this.clearClusters();

        for (int i = 0; i < data.size(); i++) {
            double minDistance = Double.MAX_VALUE;
            int minCentroid = -1;

            FeatureVector vector = data.get(i);

            for (int j = 0; j < centroids.size(); j++) {
                if(minDistance > vector.distance(centroids.get(j))) {
                    minDistance = vector.distance(centroids.get(j));
                    minCentroid = j;
                }
            }

            clusters.get(minCentroid).add(vector);
        }
    }
}
