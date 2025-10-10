# Soft Computing Library

A comprehensive Java library for soft computing algorithms including Genetic Algorithms (GA) and other evolutionary computation techniques.

## Project Structure

```
SoftComutingLib/
├── README.md
├── .gitignore
├── CaseStudy/                      # Case studies and examples
└── GA/                             # Genetic Algorithm implementation
    ├── GeneticAlgorithm.java       # Main GA engine
    ├── chromosome/                 # Chromosome representations
    │   ├── Chromosome.java
    │   ├── BinaryChromosome.java
    │   ├── IntegerChromosome.java
    │   └── FloatChromosome.java
    ├── interfaces/                 # Core interfaces
    │   ├── FitnessFunction.java
    │   ├── InfeasibilityHandler.java
    │   ├── SelectionMethod.java
    │   ├── CrossoverMethod.java
    │   ├── MutationMethod.java
    │   └── ReplacementStrategy.java
    └── operator/                   # GA operators
        ├── selection/
        │   ├── RouletteWheelSelection.java
        │   └── TournamentSelection.java
        ├── crossover/
        │   ├── SinglePointCrossover.java
        │   ├── NPointCrossover.java
        │   └── UniformCrossover.java
        ├── mutation/
        │   ├── BitFlipMutation.java
        │   ├── SwapMutation.java
        │   └── UniformMutation.java
        └── replacement/
            ├── GenerationalReplacement.java
            ├── SteadyStateReplacement.java
            └── ElitismReplacement.java
```

## Features

### Genetic Algorithm Components

#### Chromosome Types

-   **BinaryChromosome**: Represents solutions as arrays of 0s and 1s
-   **IntegerChromosome**: Represents solutions as arrays of integers
-   **FloatChromosome**: Represents solutions as arrays of floating-point numbers

#### Selection Methods

-   **Roulette Wheel Selection**: Probability-based selection proportional to fitness
-   **Tournament Selection**: Competition-based selection among random groups

#### Crossover Operators

-   **Single-Point Crossover**: Exchange genes at one crossover point
-   **N-Point Crossover**: Exchange genes between n crossover points
-   **Uniform Crossover**: Randomly exchange individual genes

#### Mutation Operators

-   **Bit Flip Mutation**: For binary chromosomes
-   **Swap Mutation**: For integer chromosomes
-   **Uniform Mutation**: For FP chromosomes

#### Replacement Strategies

-   **Generational Replacement**: Replace entire population
-   **Steady-State Replacement**: Replace worst individuals
-   **Elitism Replacement**: Preserve best individuals

## Getting Started

### Prerequisites

-   Java 8 or higher
-   IDE supporting Java development (Eclipse, IntelliJ IDEA, VS Code)

## Core Interfaces

### FitnessFunction

Defines how to evaluate the quality of solutions.

### SelectionMethod

Defines how parents are selected for reproduction.

### CrossoverMethod

Defines how parent chromosomes are combined to create offspring.

### MutationMethod

Defines how chromosomes are mutated to introduce variation.

### ReplacementStrategy

Defines how the new generation replaces the old population.

### InfeasibilityHandler

Handles invalid or infeasible solutions.

## Examples

Check the `CaseStudy/` directory for practical examples and use cases demonstrating how to apply the genetic algorithm to solve various optimization problems.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request
