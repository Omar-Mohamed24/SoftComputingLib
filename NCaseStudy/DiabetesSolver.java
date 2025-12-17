package NCaseStudy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import NN.NeuralNetwork;
import NN.activation.ReLU;
import NN.activation.Sigmoid;
import NN.initialization.Xavier;
import NN.layers.Layer;
import NN.loss.CrossEntropy;
import NN.utils.DataUtils;
import NN.utils.Matrix;

/**
 * Case Study: Pima Indians Diabetes Prediction.
 * Problem: Binary Classification (0 = No Diabetes, 1 = Diabetes).
 *
 * Dataset Details:
 * - 8 Input Features (Pregnancies, Glucose, BP, SkinThickness, Insulin, BMI,
 * Pedigree, Age)
 * - 1 Output Class (0 or 1)
 *
 * Architecture Strategy:
 * - Input Layer: 8 Neurons
 * - Hidden Layer 1: 12 Neurons (ReLU)
 * - Hidden Layer 2: 8 Neurons (ReLU)
 * - Output Layer: 1 Neuron (Sigmoid) -> Probability of Diabetes
 */
public class DiabetesSolver {
    public static void main(String[] args) {
        System.out.println("--- Pima Indians Diabetes Predictor ---");

        List<double[]> inputsList = new ArrayList<>();
        List<double[]> targetsList = new ArrayList<>();

        try {
            readData("C:\\vs\\SoftComutingLib\\NCaseStudy\\diabetes.csv", inputsList, targetsList);
            System.out.println("Dataset Loaded: " + inputsList.size() + " samples.");
        } catch (IOException e) {
            System.err.println("Could not read diabetes.csv. Using dummy data for demonstration.");
        }

        double[][] X = inputsList.toArray(new double[0][0]);
        double[][] Y = targetsList.toArray(new double[0][0]);

        System.out.println("Normalizing Data...");
        DataUtils.normalize(X);

        DataUtils.Split split = DataUtils.trainTestSplit(X, Y, 0.2);
        System.out.println("Training Set: " + split.X_train.cols + " samples");
        System.out.println("Test Set:     " + split.X_test.cols + " samples");

        System.out.println("Building Network Architecture...");
        NeuralNetwork nn = new NeuralNetwork();

        // Layer 1: 8 Inputs -> 12 Hidden (ReLU)
        nn.addLayer(new Layer(8, 12, new ReLU(), new Xavier()));

        // Layer 2: 12 Hidden -> 8 Hidden (ReLU)
        nn.addLayer(new Layer(12, 8, new ReLU(), new Xavier()));

        // Output Layer: 8 Hidden -> 1 Output (Sigmoid)
        nn.addLayer(new Layer(8, 1, new Sigmoid(), new Xavier()));

        nn.setLossFunction(new CrossEntropy());

        int epochs = 10000;
        double learningRate = 0.01;

        System.out.println("Starting Training...");
        long startTime = System.currentTimeMillis();

        nn.fit(split.X_train, split.Y_train, epochs, learningRate);

        long endTime = System.currentTimeMillis();
        System.out.println("Training Time: " + (endTime - startTime) + "ms");

        System.out.println("Evaluating on Test Set...");
        Matrix predictions = nn.predict(split.X_test);

        evaluateBinaryClassification(predictions, split.Y_test);
        predictNewPatient(nn);
    }

    private static void predictNewPatient(NeuralNetwork nn) {
        System.out.println("\n--- Patient Prediction Scenarios ---");

        // Case 1: Likely Positive (High Risk)
        // Characteristics: Many pregnancies, Very High Glucose, High BMI, Older
        // Expected Output: > 50% (Positive)
        double[] highRiskFeatures = { 0.6, 0.9, 0.7, 0.5, 0.7, 0.8, 0.6, 0.7 };
        predictAndPrint(nn, "High Risk Patient", highRiskFeatures);

        // Case 2: Likely Negative (Low Risk)
        // Characteristics: Few pregnancies, Low Glucose, Healthy BMI, Young
        // Expected Output: < 50% (Negative)
        double[] lowRiskFeatures = { 0.1, 0.2, 0.4, 0.1, 0.1, 0.2, 0.1, 0.1 };
        predictAndPrint(nn, "Low Risk Patient", lowRiskFeatures);

        // Case 3: Borderline / Mixed
        // Characteristics: Middle aged, Moderate Glucose, Overweight but not Obese
        // Expected Output: Uncertain (Values closer to 50%)
        double[] borderlineFeatures = { 0.5, 0.55, 0.6, 0.2, 0.3, 0.45, 0.3, 0.5 };
        predictAndPrint(nn, "Borderline Patient", borderlineFeatures);
    }

    /**
     * Helper to run prediction and print results nicely.
     */
    private static void predictAndPrint(NeuralNetwork nn, String description, double[] features) {
        Matrix input = Matrix.fromArray(features);
        Matrix output = nn.predict(input);
        double probability = output.data[0][0];

        System.out.println("\nCase: " + description);
        System.out.println("  Features (Normalized): " + java.util.Arrays.toString(features));
        System.out.printf("  Diabetes Probability:  %.2f%%\n", probability * 100);
        System.out.println("  Verdict: " + (probability > 0.5 ? "Positive (Diabetes)" : "Negative (No Diabetes)"));
    }

    private static void evaluateBinaryClassification(Matrix predictions, Matrix targets) {
        int tp = 0, tn = 0, fp = 0, fn = 0;
        double threshold = 0.5;

        for (int i = 0; i < predictions.cols; i++) {
            double predictedProb = predictions.data[0][i];
            double actualLabel = targets.data[0][i];

            int predictedClass = predictedProb >= threshold ? 1 : 0;
            int actualClass = (int) actualLabel;

            if (predictedClass == 1 && actualClass == 1)
                tp++;
            else if (predictedClass == 0 && actualClass == 0)
                tn++;
            else if (predictedClass == 1 && actualClass == 0)
                fp++;
            else if (predictedClass == 0 && actualClass == 1)
                fn++;
        }

        double accuracy = (double) (tp + tn) / (tp + tn + fp + fn);
        double precision = (tp + fp) > 0 ? (double) tp / (tp + fp) : 0;
        double recall = (tp + fn) > 0 ? (double) tp / (tp + fn) : 0;
        double f1 = (precision + recall) > 0 ? 2 * (precision * recall) / (precision + recall) : 0;

        System.out.println("\n--- Final Metrics ---");
        System.out.printf("Accuracy:  %.2f%%\n", accuracy * 100);
        System.out.printf("Precision: %.2f\n", precision);
        System.out.printf("Recall:    %.2f\n", recall);
        System.out.printf("F1 Score:  %.2f\n", f1);
    }

    private static void readData(String filepath, List<double[]> x, List<double[]> y) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        String line;
        boolean isHeader = true;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty())
                continue;

            if (isHeader) {
                try {
                    Double.parseDouble(line.split(",")[0]);
                    isHeader = false;
                } catch (NumberFormatException e) {
                    isHeader = false;
                    continue;
                }
            }

            String[] parts = line.split(",");
            if (parts.length < 9)
                continue;

            double[] features = new double[8];
            for (int i = 0; i < 8; i++) {
                features[i] = Double.parseDouble(parts[i]);
            }
            x.add(features);

            double[] label = new double[1];
            label[0] = Double.parseDouble(parts[8]);
            y.add(label);
        }
        br.close();
    }
}
