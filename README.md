# Soft Computing Library

A Java library that groups three soft-computing toolkits in one codebase:

- Genetic Algorithms (GA)
- Fuzzy Logic (FL)
- Feedforward Neural Networks (NN)

It also includes practical case studies for optimization, fuzzy decision systems, and classification.

## What Is Included

### 1) Genetic Algorithm Toolkit (`GA/`)

Core engine:

- `GeneticAlgorithm.java`: population lifecycle, evaluation, selection, crossover, mutation, and replacement

Chromosome representations:

- `BinaryChromosome`
- `IntegerChromosome`
- `FloatChromosome`

Extensibility interfaces:

- `FitnessFunction`
- `InfeasibilityHandler`
- `SelectionMethod`
- `CrossoverMethod`
- `MutationMethod`
- `ReplacementStrategy`

Built-in operators:

- Selection: `RouletteWheelSelection`, `TournamentSelection`
- Crossover: `SinglePointCrossover`, `NPointCrossover`, `UniformCrossover`, `OrderOneCrossover`
- Mutation: `BitFlipMutation`, `SwapMutation`, `UniformMutation`
- Replacement: `GenerationalReplacement`, `SteadyStateReplacement`, `ElitismReplacement`

### 2) Fuzzy Logic Toolkit (`FL/`)

Core classes:

- `FuzzySystem`
- `LinguisticVariable`
- `FuzzySet`
- `Rule`
- `RuleBase`

Inference methods:

- `MamdaniInference`
- `SugenoInference`

Defuzzification methods:

- `Centroid`
- `MeanOfMaximum`

Membership functions:

- `TriangularMF`
- `TrapezoidalMF`
- `GaussianMF`

Logic operators:

- T-Norms: `Min_T_Norm`, `Product_T_Norm`
- S-Norms: `Max_S_Norm`, `Sum_S_Norm`

### 3) Neural Network Toolkit (`NN/`)

Core classes:

- `NeuralNetwork`
- `Layer`
- `Matrix` utility class

Activation functions:

- `ReLU`
- `Sigmoid`
- `Tanh`
- `Linear`

Weight initialization:

- `Xavier`
- `RandomUniform`

Loss functions:

- `MSE`
- `CrossEntropy`

Data utilities:

- Normalization
- Train/test split
- Dataset shuffle

## Case Studies

### GA Case Studies (`CaseStudy/`)

- `CaseStudyApplication.java`
- 0/1 Knapsack problem (`CaseStudy/knapsack/`)
- N-Queens problem (`CaseStudy/NQueens/`)

### Fuzzy Logic Case Study (`FCaseStudy/`)

- `TippingSolver.java`
- Advanced restaurant tipping system using waiting time, food quality, and service quality

### Neural Network Case Study (`NCaseStudy/`)

- `DiabetesSolver.java`
- Binary classification on Pima Indians Diabetes dataset (`diabetes.csv`)

## Project Structure

```text
SoftComutingLib/
|-- README.md
|-- .gitignore
|-- CaseStudy/
|   |-- CaseStudyApplication.java
|   |-- knapsack/
|   |   |-- Item.java
|   |   |-- KnapsackFitness.java
|   |   `-- KnapsackInfeasibilityHandler.java
|   `-- NQueens/
|       |-- NQueenFitness.java
|       `-- NQueensInfeasibilityHandler.java
|-- FCaseStudy/
|   `-- TippingSolver.java
|-- FL/
|   |-- FuzzySet.java
|   |-- FuzzySystem.java
|   |-- LinguisticVariable.java
|   |-- Rule.java
|   |-- RuleBase.java
|   |-- defuzzify/
|   |   |-- Centroid.java
|   |   `-- MeanOfMaximum.java
|   |-- inference/
|   |   |-- MamdaniInference.java
|   |   `-- SugenoInference.java
|   |-- interfaces/
|   |   |-- DefuzzifyMethod.java
|   |   |-- InferenceMethod.java
|   |   |-- MembershipFunction.java
|   |   |-- S_Norm.java
|   |   `-- T_Norm.java
|   |-- membership_functions/
|   |   |-- GaussianMF.java
|   |   |-- TrapezoidalMF.java
|   |   `-- TriangularMF.java
|   `-- operators/
|       |-- Max_S_Norm.java
|       |-- Min_T_Norm.java
|       |-- Product_T_Norm.java
|       `-- Sum_S_Norm.java
|-- GA/
|   |-- GeneticAlgorithm.java
|   |-- chromosome/
|   |   |-- BinaryChromosome.java
|   |   |-- Chromosome.java
|   |   |-- FloatChromosome.java
|   |   `-- IntegerChromosome.java
|   |-- interfaces/
|   |   |-- CrossoverMethod.java
|   |   |-- FitnessFunction.java
|   |   |-- InfeasibilityHandler.java
|   |   |-- MutationMethod.java
|   |   |-- ReplacementStrategy.java
|   |   `-- SelectionMethod.java
|   `-- operator/
|       |-- crossover/
|       |   |-- NPointCrossover.java
|       |   |-- OrderOneCrossover.java
|       |   |-- SinglePointCrossover.java
|       |   `-- UniformCrossover.java
|       |-- mutation/
|       |   |-- BitFlipMutation.java
|       |   |-- SwapMutation.java
|       |   `-- UniformMutation.java
|       |-- replacement/
|       |   |-- ElitismReplacement.java
|       |   |-- GenerationalReplacement.java
|       |   `-- SteadyStateReplacement.java
|       `-- selection/
|           |-- RouletteWheelSelection.java
|           `-- TournamentSelection.java
|-- NCaseStudy/
|   |-- diabetes.csv
|   `-- DiabetesSolver.java
`-- NN/
    |-- NeuralNetwork.java
    |-- activation/
    |   |-- ActivationFunction.java
    |   |-- Linear.java
    |   |-- ReLU.java
    |   |-- Sigmoid.java
    |   `-- Tanh.java
    |-- initialization/
    |   |-- Initializer.java
    |   |-- RandomUniform.java
    |   `-- Xavier.java
    |-- layers/
    |   `-- Layer.java
    |-- loss/
    |   |-- CrossEntropy.java
    |   |-- LossFunction.java
    |   `-- MSE.java
    `-- utils/
        |-- DataUtils.java
        `-- Matrix.java
```

## Getting Started

### Prerequisites

- JDK 8 or later
- Any Java IDE (VS Code, IntelliJ IDEA, Eclipse) or command line

### Run Examples

```powershell
# GA case studies (N-Queens is enabled by default in main)
java CaseStudy.CaseStudyApplication

# Fuzzy tipping case study
java FCaseStudy.TippingSolver

# Neural network diabetes case study
java NCaseStudy.DiabetesSolver
```

## Quick Usage Pattern

### Genetic Algorithm

1. Configure population and chromosome type.
2. Provide `FitnessFunction` and optional `InfeasibilityHandler`.
3. Select operators (selection/crossover/mutation/replacement).
4. Run with `ga.run()` and inspect `ga.getBestChromosome()`.

### Fuzzy Logic System

1. Create `FuzzySystem`.
2. Add linguistic variables and fuzzy sets.
3. Add rules.
4. Call `evaluate(Map<String, Double>)` for crisp outputs.

### Neural Network

1. Build layers with activation and initializer.
2. Set loss function.
3. Train via `fit(X, Y, epochs, learningRate)`.
4. Infer via `predict(...)`.

## Notes

- `NCaseStudy/DiabetesSolver.java` currently uses an absolute path for `diabetes.csv`. Update this if your local path is different.

## Contributing

1. Fork the repository.
2. Create a feature branch.
3. Commit clear, focused changes.
4. Add or update tests/case studies where relevant.
5. Open a pull request.