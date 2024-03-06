import java.util.HashMap;
import java.util.Map;

class ImageSegmentation {

    private int segmentCount;
    private int largestColor;
    private int largestSize;
    private int N;
    private int[][] image;
    private int[] parent;
    private int[] size;

    public ImageSegmentation(int N, int[][] inputImage) {
        this.N = N;
        image = inputImage;
        parent = new int[N * N];
        size = new int[N * N];
        segmentCount = 0;
        largestColor = 0;
        largestSize = 0;
        initialize();
    }

    private void initialize() {
        for (int i = 0; i < N * N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    private int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]]; // path compression
            p = parent[p];
        }
        return p;
    }

    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    public int countDistinctSegments() {
        Map<Integer, Integer> segmentMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (image[i][j] != 0) {
                    int currentColor = image[i][j];
                    if (!segmentMap.containsKey(currentColor)) {
                        segmentMap.put(currentColor, segmentCount++);
                    }
                    int segmentIndex = segmentMap.get(currentColor);
                    if (i > 0 && image[i - 1][j] == currentColor) {
                        union(i * N + j, (i - 1) * N + j);
                    }
                    if (j > 0 && image[i][j - 1] == currentColor) {
                        union(i * N + j, i * N + j - 1);
                    }
                }
            }
        }
        return segmentCount;
    }

    public int[] findLargestSegment() {
        Map<Integer, Integer> segmentSizes = new HashMap<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (image[i][j] != 0) {
                    int root = find(i * N + j);
                    int currentSize = size[root];
                    int currentColor = image[i][j];
                    segmentSizes.put(root, currentSize);
                    if (currentSize > largestSize || (currentSize == largestSize && currentColor < largestColor)) {
                        largestSize = currentSize;
                        largestColor = currentColor;
                    }
                }
            }
        }
        return new int[]{largestSize, largestColor};
    }

    // private object mergeSegment (object XXX, ...){ 
        // Maybe you can use user-defined function to
        // facilitate you implement mergeSegment method. 
    // }

    public static void main(String args[]) {

        // Example 1:
        int[][] inputImage1 = {
            {0, 0, 0},
            {0, 1, 1},
            {0, 0, 1}
        };

        System.out.println("Example 1:");

        ImageSegmentation s = new ImageSegmentation(3, inputImage1);
        System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());

        int[] largest = s.findLargestSegment();
        System.out.println("Size of the Largest Segment: " + largest[0]);
        System.out.println("Color of the Largest Segment: " + largest[1]);


        // Example 2:
        int[][] inputImage2 = {
               {0, 0, 0, 3, 0},
               {0, 2, 3, 3, 0},
               {1, 2, 2, 0, 0},
               {1, 2, 2, 1, 1},
               {0, 0, 1, 1, 1}
        };

        System.out.println("\nExample 2:");

        s = new ImageSegmentation(5, inputImage2);
        System.out.println("Number of Distinct Segments: " + s.countDistinctSegments());

        largest = s.findLargestSegment();
        System.out.println("Size of the Largest Segment: " + largest[0]);
        System.out.println("Color of the Largest Segment: " + largest[1]);

    }

}

