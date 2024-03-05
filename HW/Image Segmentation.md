# Source by Chien-Yu Chen@NTU  
# HW1 - Image Segmentation
In HW1, the task involves segmenting input images into multiple parts, referred to as segments. Your objective for this assignment is to utilize Union-Find algorithm(s) to effectively extract key information about these segments from the input image.

# Introduction
*Image Segmentation* is pivotal in Computer Vision, involving the division of images into distinct regions. This task encompasses various subcategories, including *Semantic Segmentation, Instance Segmentation, Panoramic Segmentation*, and more.To simplify the task, we will concentrate on Semantic Segmentation in this assignment and subsequent descriptions. 

If you want to learn more about Image Segmentation, please refer to Image Classification vs Semantic Segmentation vs Instance Segmentation(https://nirmalamurali.medium.com/image-classification-vs-semantic-segmentation-vs-instance-segmentation-625c33a08d50) or 影像分割 Image Segmentation — 語義分割 Semantic Segmentation(1)(https://medium.com/ching-i/影像分割-image-segmentation-語義分割-semantic-segmentation-1-53a1dde9ed92)

# Description
In the given N x N image, represented as a 2D array of integers, color values serve to differentiate objects. A color value of 0 denotes the uncolored background, while any non-zero color value indicates the presence of an object. Adjacent pixels with the same color constitute a segment.

Your task is to develop a Java program that employs Union-Find algorithms to analyze the input image. The program should generate the necessary information, specifically:

* *Number of Distinct Segments.*
* *Size and Color of the Largest Segment.*

Here are some additional instructions:

* Uncolored background pixels *are not considered as Segments.*
* If there are unconnected segments with the same color, they will *be treated as distinct Segments,* and their pixels will not be combined when calculating the size of a segment, i.e. the number of pixels within a segment.
* If two or more segments are of the same size, the segment with the smallest color index is considered as the Larger Segment.

# Hint
* How does the Union-Find algorithm(s) merge segments?
* What kind of Union-Find algorithm are more efficient for this homework?
* Maybe you can use user-defined function to facilitate you implement mergeSegment method.

# Template

```cmd
class ImageSegmentation {
    
    private int segmentCount;
    private int largestColor;
    private int largestSize;

    public ImageSegmentation(int N, int[][] inputImage) {
        // Initialize a N-by-N image
    }

    public int countDistinctSegments() {
        // Count the number of distinct segments in the image.
        return segmentCount;
    }

    public int[] findLargestSegment() {
        // Find the largest connected segment and return an array
        // containing the number of pixels and the color of the segment.
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
```
# Expected Output
```text
Example 1:
Number of Distinct Segments: 1
Size of the Largest Segment: 3
Color of the Largest Segment: 1

Example 2:
Number of Distinct Segments: 4
Size of the Largest Segment: 5
Color of the Largest Segment: 1
```
# TestCase
Time Limit: 300ms

* N: height and width of the input image
* C: number of colors in the input image
* 20 points: N <= 3, C <= 2 (Easy)
* 20 points: N <= 5, C <= 5 (Special Case)
* 20 points: N <= 25, C <= 10
* 20 points: N <= 100, C <= 50
* 20 points: N <= 500, C <= 100
