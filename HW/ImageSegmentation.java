import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	
    public static void test(String[] args){
        ImageSegmentation s;
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0])){
            JSONArray all = (JSONArray) jsonParser.parse(reader);
            int count = 0;
            for(Object CaseInList : all){
                count++;
                JSONObject aCase = (JSONObject) CaseInList;
                JSONArray dataArray = (JSONArray) aCase.get("data");

                // JSONObject data = (JSONObject) aCase.get("data");
                // JSONArray dataArray = (JSONArray) data.get("data");

                int testSize = 0; int waSize = 0;
                System.out.print("Case ");
                System.out.println(count);
                for (Object dataObject : dataArray) {
                    JSONObject dataDetails = (JSONObject) dataObject;
                    int N = ((Long) dataDetails.get("N")).intValue();
                    JSONArray imageArray = (JSONArray) dataDetails.get("image");

                    int[][] image = new int[imageArray.size()][];
                    for (int i = 0; i < imageArray.size(); i++) {
                        JSONArray row = (JSONArray) imageArray.get(i);
                        image[i] = new int[row.size()];
                        for (int j = 0; j < row.size(); j++) {
                            image[i][j] = ((Long) row.get(j)).intValue();
                        }
                    }
                    // System.out.println("N: " + N);
                    // System.out.println("Image: " + Arrays.deepToString(image));

                    s = new ImageSegmentation(N, image);

                    int distinctSegments = ((Long) dataDetails.get("DistinctSegments")).intValue();

                    JSONArray largestSegmentArray = (JSONArray) dataDetails.get("LargestSegment");
                    int largestColor = ((Long) largestSegmentArray.get(0)).intValue();
                    int largestSize = ((Long) largestSegmentArray.get(1)).intValue();

                    int ans1 = s.countDistinctSegments();
                    int ans2 = s.findLargestSegment()[0];
                    int ans3 = s.findLargestSegment()[1];

                    testSize++;
                    if(ans1==distinctSegments && ans2==largestColor && ans3==largestSize){
                        // System.out.println("AC");

                    }else{
                        waSize++;
                        // System.out.println("WA");
                    }
                }
                System.out.println("Score: " + (testSize-waSize) + " / " + testSize + " ");

            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static int[] JSONArraytoIntArray(JSONArray x){
        int sizeLim = x.size();
        int MyInt[] = new int[sizeLim];
        for(int i=0;i<sizeLim;i++){
            MyInt[i]= Integer.parseInt(x.get(i).toString());
        }
        return MyInt;
    }

    public static void main(String[] args) {
        test(args);
    }
}