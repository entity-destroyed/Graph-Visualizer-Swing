# Graph Visualizer

Graph Visualizer is a Java Swing-based application for
visualizing mathematical graphs. Users can input
mathematical expressions, and the application will render the corresponding graph.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## Features

- Input mathematical expressions to render their corresponding graphs.
- Supports various mathematical functions and constants.

## Installation


### Prerequisites

- Java Development Kit (JDK) 18
- Maven 3.6+

### Build from Source

1. **Clone the repository:**

    ```bash
    git clone https://github.com/entity_destroyed/graph-visualizer-swing.git
    cd graph-visualizer-swing
    ```

2. **Build the project using Maven:**

    ```bash
    mvn clean install
    ```

3. **Run the application:**

    ```bash
    java -jar target/Graph_Visualizer_Swing-1.0-SNAPSHOT-shaded.jar
    ```

## Usage

1. **Launch the application.**
2. **Choose an option:** _New empty pane_, or _Load previous_.
   - If you have chosen _New empty pane_, now click _Add new_.
3. **Enter a mathematical expression** in the input field (e.g., `sin(x)`, `x^2 + 3*x - 2`).
4. **Hit enter** to visualize the graph.

## Configuration

Configuration options are defined in the `config.properties` file. Here are the adjustable parameters:

- `g.step`: The step interval for graph calculations (e.g., `0.1`).
- `g.scale`: The scale factor for rendering the graph (e.g., `10`).
- `g.begin`: The starting value of the range (e.g., `-50`).
- `g.end`: The ending value of the range (e.g., `50`).

Example `config.properties`:

```properties
g.step=0.1
g.scale=10
g.begin=-50
g.end=50
```

## Development

### Dependencies

The project uses the following dependencies:
- [Javaluator](https://github.com/fathzer/javaluator)
- [JUnit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)

### Building and Testing

1. **Clone the repository** and navigate to the project directory.
2. **Build the project:**

    ```bash
    mvn clean install
    ```

3. **Run tests:**

    ```bash
    mvn test
    ```

### Important Files and Directories

- `src/main/java/com/graphVisualizer`: Main application source code.
- `src/test/java/com/graphVisualizer`: Test cases.
- `pom.xml`: Project Object Model file containing project dependencies and build configurations.

## Contributing

Contributions are welcome! Please follow these steps to contribute:

1. **Fork the repository** on GitHub.
2. **Clone your fork:**

    ```bash
    git clone https://github.com/yourusername/graph-visualizer-swing.git
    cd graph-visualizer-swing
    ```

3. **Create a new branch** for your feature or bugfix:

    ```bash
    git checkout -b feature/your-feature-name
    ```

4. **Make your changes** and commit them with descriptive messages.
5. **Push your changes:**

    ```bash
    git push origin feature/your-feature-name
    ```

6. **Create a pull request** on GitHub.


## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.