# Chaikin's Algorithm Visualizer

A Java-based visualization tool for **Chaikin's Algorithm**, a corner-cutting method used to generate smooth curves from a sequence of control points.

## Project Structure

- **`Main.java`**: The application entry point. It initializes the UI components and starts the animation thread.
- **`ChaikinPanel.java`**: The core component that implements Chaikin's Algorithm logic and handles the rendering of points and curves.
- **`InputHandler.java`**: Manages user interactions, including mouse clicks for placing points and keyboard shortcuts for controlling the simulation.
- **`Makefile`**: Simplifies the build and execution process.

## How to Run

Ensure you have a Java Development Kit (JDK) installed.

### Using Make

To compile and run the application in one step:
```bash
make run
```

Other available commands:
- `make compile`: Compiles the source code into the `build/` directory.
- `make clean`: Removes the `build/` directory and compiled classes.

## Controls

- **Left Mouse Click**: Place control points (white dots) on the canvas.
- **Enter**: Start the smoothing animation.
- **Space**: Clear the canvas and reset the application.
- **Escape**: Close the application.

## Algorithm Overview

Chaikin's algorithm works by "cutting the corners" of a polyline. In each iteration, every interior control point is replaced by two new points:
- One at **1/4** of the distance along the edge to the next point.
- One at **3/4** of the distance along the edge to the next point.

This process is repeated to create increasingly smooth approximations of a curve.
