/*
 * Will C.
 * GUI Project
 * Main Application Class - Vector/Matrix Calculator
 * includes all layout use of other classes
 * contains methods for graphing, resizing, and creating input panes
 */

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.scene.paint.*;

public class VectorCalculator extends Application {

    // set new bounds for graph if values are greater than default
    public static void setNewXBound(double x1, double x2, boolean addition, Graph graph) {
      double max = Math.max(Math.abs(x1), Math.abs(x2));
      if (addition) {
        max = Math.max(max, Math.abs(x1+x2));
      }
      if (max > 10.0) {
        graph.setXBound((double)Math.round(1.2 * max));
      } else {
        graph.setXBound(10.0);
      }
    }

    public static void setNewYBound(double y1, double y2, boolean addition, Graph graph) {
      double max = Math.max(Math.abs(y1), Math.abs(y2));
      if (addition) {
        max = Math.max(max, Math.abs(y1+y2));
      }
      if (max > 10.0) {
        graph.setYBound((double)Math.round(1.2 * max));
      } else {
        graph.setYBound(10.0);
      }
    }

    // graph the two given vectors and their sum; return the sum vector
    public static Vector vectorAddition(double x1, double y1, double s1, double x2, double y2, double s2, Graph graph) {
      Vector vector1 = new Vector(x1, y1, graph);
      Vector vector2 = new Vector(x2, y2, graph);
      vector1.scaleVector(s1);
      vector2.scaleVector(s2);
      Vector sum = vector1.addVector(vector2);
      setNewXBound(vector1.getX(), vector2.getX(), true, graph);
      setNewYBound(vector1.getY(), vector2.getY(), true, graph);
      Line line1 = vector1.toLine(Color.RED);
      Line line2 = vector2.toLine(Color.BLUE, vector1);
      Line line3 = sum.toLine(Color.BLUEVIOLET);
      graph.clearGraph();
      graph.addToLegend("v1", Color.RED);
      graph.addToLegend("v2", Color.BLUE);
      graph.addToLegend("v1 + v2", Color.BLUEVIOLET);
      graph.showLegend();
      graph.getChildren().addAll(line1, line2, line3);
      return sum;
    }

    // return the dot product of two vectors and graph the vectors
    public static double vectorDotProduct(double x1, double y1, double s1, double x2, double y2, double s2, Graph graph) {
      Vector vector1 = new Vector(x1, y1, graph);
      Vector vector2 = new Vector(x2, y2, graph);
      vector1.scaleVector(s1);
      vector2.scaleVector(s2);
      setNewXBound(vector1.getX(), vector2.getX(), false, graph);
      setNewYBound(vector1.getY(), vector2.getY(), false, graph);
      double product = vector1.dotProduct(vector2);
      Line line1 = vector1.toLine(Color.RED);
      Line line2 = vector2.toLine(Color.BLUE);
      graph.clearGraph();
      graph.addToLegend("v1", Color.RED);
      graph.addToLegend("v2", Color.BLUE);
      graph.showLegend();
      graph.getChildren().addAll(line1, line2);
      return product;
    }

    // graph the given vector and the product of that vector with the matrix; return the product vector
    public static Vector matrixVectorMultiply(double[][] values, double x, double y, Graph graph) {
      Vector vector = new Vector(x, y, graph);
      Matrix matrix = new Matrix(values, graph);
      Vector product = matrix.multiplyByVector(vector);
      setNewXBound(vector.getX(), product.getX(), false, graph);
      setNewYBound(vector.getY(), product.getY(), false, graph);
      Line line1 = vector.toLine(Color.RED);
      Line line2 = product.toLine(Color.BLUE);
      graph.clearGraph();
      graph.addToLegend("v", Color.RED);
      graph.addToLegend("Mv", Color.BLUE);
      graph.showLegend();
      graph.getChildren().addAll(line1, line2);
      return product;
    }

    // return the product matrix of the two given matrices; graph where the transformed unit vectors are from the product matrix
    public static Matrix matrixMultiply(double[][] values1, double[][] values2, Graph graph) {
       Matrix matrix1 = new Matrix(values1, graph);
       Matrix matrix2 = new Matrix(values2, graph);
       Matrix product = matrix1.multiplyByMatrix(matrix2);
       Vector unitX = new Vector(1.0, 0.0, graph);
       Vector unitY = new Vector(0.0, 1.0, graph);
       Vector transUnitX = product.multiplyByVector(unitX);
       Vector transUnitY = product.multiplyByVector(unitY);
       setNewXBound(transUnitX.getX(), transUnitY.getX(), false, graph);
       setNewYBound(transUnitX.getY(), transUnitY.getY(), false, graph);
       Line line1 = transUnitX.toLine(Color.RED);
       Line line2 = transUnitY.toLine(Color.BLUE);
       graph.clearGraph();
       graph.addToLegend("new î", Color.RED);
       graph.addToLegend("new ĵ", Color.BLUE);
       graph.showLegend();
       graph.getChildren().addAll(line1, line2);
       return product;
    }

    // format text fields to look like a vector; return where the next input field should go
    public static int createVectorField(TextField s, TextField x, TextField y, int width, int startPos, boolean hasScalar, GridPane function) {
      int pos = startPos;
      if (hasScalar == true) {
        s.setPrefWidth(width);
        function.add(s, pos, 0, 1, 2); pos++;
      }
      Text openBracket = new Text("[");
      openBracket.setFont(Font.font ("Verdana", 40));
      function.add(openBracket, pos, 0, 1, 2); pos++;
      x.setPrefWidth(width);
      function.add(x, pos, 0);
      y.setPrefWidth(width);
      function.add(y, pos, 1); pos++;
      Text closeBracket = new Text("]");
      closeBracket.setFont(Font.font ("Verdana", 40));
      function.add(closeBracket, pos, 0, 1, 2);
      return pos+1;
    }

    // display a vector answer nicely as a vector
    public static void addVectorAnswer(Text openBracket, Text x, Text y, Text closeBracket, int startPos, GridPane function) {
      int pos = startPos;
      function.add(openBracket, pos, 0, 1, 2); pos++;
      function.add(x, pos, 0);
      function.add(y, pos, 1); pos++;
      function.add(closeBracket, pos, 0, 1, 2);
    }

    // format text fields to look like a matrix; return where the next input field should go
    public static int createMatrixField(TextField m00, TextField m10, TextField m01, TextField m11, int width, int startPos, GridPane function) {
      int pos = startPos;
      Text openBracket = new Text("[");
      openBracket.setFont(Font.font ("Verdana", 40));
      function.add(openBracket, pos, 0, 1, 2); pos++;
      m00.setPrefWidth(width);
      function.add(m00, pos, 0);
      m10.setPrefWidth(width);
      function.add(m10, pos, 1); pos++;
      m01.setPrefWidth(width);
      function.add(m01, pos, 0);
      m11.setPrefWidth(width);
      function.add(m11, pos, 1); pos++;
      Text closeBracket = new Text("]");
      closeBracket.setFont(Font.font ("Verdana", 40));
      function.add(closeBracket, pos, 0, 1, 2);
      return pos + 1;
    }

    // display a matrix answer nicely as a matrix
    public static void addMatrixAnswer(Text openBracket, Text m00, Text m10, Text m01, Text m11, Text closeBracket, int startPos, GridPane function) {
      int pos = startPos;
      function.add(openBracket, pos, 0, 1, 2); pos++;
      function.add(m00, pos, 0);
      function.add(m10, pos, 1); pos++;
      function.add(m01, pos, 0);
      function.add(m11, pos, 1); pos++;
      function.add(closeBracket, pos, 0, 1, 2);
    }

    // create and return a pane that takes inputs for two vectors and displays dot product or sum
    // when equals button is pressed, run the corresponding function
    public static GridPane createVectorPane(String sign, Graph graph, GridPane input) {
      GridPane function = new GridPane();
      int textFieldWidth = 40;
      int pos = 0;
      TextField s1 = new TextField();
      TextField x1 = new TextField();
      TextField y1 = new TextField();
      pos = createVectorField(s1, x1, y1, textFieldWidth, pos, true, function);
      Text functionSign = new Text(sign);
      functionSign.setFont(Font.font ("Verdana", 30));
      function.add(functionSign, pos, 0, 1, 2); pos++;
      TextField s2 = new TextField();
      TextField x2 = new TextField();
      TextField y2 = new TextField();
      pos = createVectorField(s2, x2, y2, textFieldWidth, pos, true, function);
      Button button = new Button("=");
      function.add(button, pos, 0, 1, 2); pos++;
      Text openBracket3 = new Text("[");
      openBracket3.setFont(Font.font ("Verdana", 40));
      Text answerX = new Text("  ");
      Text answerY = new Text("  ");
      Text closeBracket3 = new Text("]");
      closeBracket3.setFont(Font.font ("Verdana", 40));
      Text answer = new Text("  ");
      if (sign.equals("+")) {
        addVectorAnswer(openBracket3, answerX, answerY, closeBracket3, pos, function);
      } else {
        function.add(answer, pos, 0, 1, 2);
        function.setMargin(answer, new Insets(5, 10, 5, 10));
      }
      button.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
          boolean nullField = false;
          TextField[] inputFields = {s1, x1, y1, s2, x2, y2};
          for (TextField field : inputFields) {
            if (field.getText() == null || field.getText().trim().isEmpty()) {
              nullField = true;
            }
          }
          for (Node child : input.getChildren()) {
            child.setStyle("-fx-background-color: #ffffff;");
          }
          if (nullField == true) {
            function.setStyle("-fx-background-color: #ff5e62;");
          } else {
            double valueS1 = Double.valueOf(s1.getText()).doubleValue();
            double valueX1 = Double.valueOf(x1.getText()).doubleValue();
            double valueY1 = Double.valueOf(y1.getText()).doubleValue();
            double valueS2 = Double.valueOf(s2.getText()).doubleValue();
            double valueX2 = Double.valueOf(x2.getText()).doubleValue();
            double valueY2 = Double.valueOf(y2.getText()).doubleValue();
            function.setStyle("-fx-background-color: #a8f7a5;");
            if (sign.equals("+")) {
              Vector sum = vectorAddition(valueX1, valueY1, valueS1, valueX2, valueY2, valueS2, graph);
              answerX.setText(String.valueOf(sum.getX()));
              answerY.setText(String.valueOf(sum.getY()));
            } else {
              double product = vectorDotProduct(valueX1, valueY1, valueS1, valueX2, valueY2, valueS2, graph);
              answer.setText(String.valueOf(product));
            }
          }
        }
      });
      function.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
      function.setStyle("-fx-background-color: #ffffff;");
      function.setPadding(new Insets(10, 10, 10, 10));
      return function;
    }

    // create and return a pane that takes inputs for a matrix and vector or two matrices and displays product
    // when equals button is pressed, run the corresponding function
    public static GridPane createMatrixPane(boolean hasVector, Graph graph, GridPane input) {
      GridPane function = new GridPane();
      int textFieldWidth = 40;
      int pos = 0;
      TextField m100 = new TextField();
      TextField m110 = new TextField();
      TextField m101 = new TextField();
      TextField m111 = new TextField();
      pos = createMatrixField(m100, m110, m101, m111, textFieldWidth, pos, function);
      TextField nullS = new TextField();
      TextField x = new TextField();
      TextField y = new TextField();
      TextField m200 = new TextField();
      TextField m210 = new TextField();
      TextField m201 = new TextField();
      TextField m211 = new TextField();
      if (hasVector == true) {
        pos = createVectorField(nullS, x, y, textFieldWidth, pos, false, function);
      } else {
        pos = createMatrixField(m200, m210, m201, m211, textFieldWidth, pos, function);
      }
      Button button = new Button("=");
      function.add(button, pos, 0, 1, 2); pos++;
      Text openBracket3 = new Text("[");
      openBracket3.setFont(Font.font ("Verdana", 40));
      Text answerX = new Text("  ");
      Text answerY = new Text("  ");
      Text answerM00 = new Text("  ");
      Text answerM10 = new Text("  ");
      Text answerM01 = new Text("  ");
      Text answerM11 = new Text("  ");
      Text closeBracket3 = new Text("]");
      closeBracket3.setFont(Font.font ("Verdana", 40));
      if (hasVector == true) {
        addVectorAnswer(openBracket3, answerX, answerY, closeBracket3, pos, function);
      } else {
        addMatrixAnswer(openBracket3, answerM00, answerM10, answerM01, answerM11, closeBracket3, pos, function);
      }
      button.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent e) {
          boolean nullField = false;
          if (hasVector == true) {
            TextField[] inputFields = {m100, m110, m101, m111, x, y};
            for (TextField field : inputFields) {
              if (field.getText() == null || field.getText().trim().isEmpty()) {
                nullField = true;
              }
            }
          } else {
            TextField[] inputFields = {m100, m110, m101, m111, m200, m210, m201, m211};
            for (TextField field : inputFields) {
              if (field.getText() == null || field.getText().trim().isEmpty()) {
                nullField = true;
              }
            }
          }
          for (Node child : input.getChildren()) {
            child.setStyle("-fx-background-color: #ffffff;");
          }
          if (nullField == true) {
            function.setStyle("-fx-background-color: #ff5e62;");
          } else {
            function.setStyle("-fx-background-color: #a8f7a5;");
            double[][] matrix1 = new double[2][2];
            matrix1[0][0] = Double.valueOf(m100.getText()).doubleValue();
            matrix1[1][0] = Double.valueOf(m110.getText()).doubleValue();
            matrix1[0][1] = Double.valueOf(m101.getText()).doubleValue();
            matrix1[1][1] = Double.valueOf(m111.getText()).doubleValue();
            if (hasVector == true) {
              double valueX = Double.valueOf(x.getText()).doubleValue();
              double valueY = Double.valueOf(y.getText()).doubleValue();
              Vector product = matrixVectorMultiply(matrix1, valueX, valueY, graph);
              answerX.setText(String.valueOf(product.getX()));
              answerY.setText(String.valueOf(product.getY()));
            } else {
              double[][] matrix2 = new double[2][2];
              matrix2[0][0] = Double.valueOf(m200.getText()).doubleValue();
              matrix2[1][0] = Double.valueOf(m210.getText()).doubleValue();
              matrix2[0][1] = Double.valueOf(m201.getText()).doubleValue();
              matrix2[1][1] = Double.valueOf(m211.getText()).doubleValue();
              Matrix product = matrixMultiply(matrix1, matrix2, graph);
              answerM00.setText(String.valueOf(product.getValues()[0][0]));
              answerM10.setText(String.valueOf(product.getValues()[1][0]));
              answerM01.setText(String.valueOf(product.getValues()[0][1]));
              answerM11.setText(String.valueOf(product.getValues()[1][1]));
            }
          }
        }
      });
      function.setBorder(new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
      function.setStyle("-fx-background-color: #ffffff;");
      function.setPadding(new Insets(10, 10, 10, 10));
      return function;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

      // create new graph and place graph in stack pane
      Graph graph = new Graph(400, 400, 10, 1, 10, 1);
      StackPane graphPane = new StackPane(graph);

      // create new grid pane to store all input panes
      GridPane inputPane = new GridPane();
      inputPane.setAlignment(Pos.CENTER_LEFT);
      inputPane.setHgap(10);

      // create pane for adding two vectors and put it in input pane
      GridPane addition = createVectorPane("+", graph, inputPane);
      inputPane.add(addition, 0, 0);

      // create pane for taking the dot product of two vectors and put it in input pane
      GridPane dotProduct = createVectorPane("•", graph, inputPane);
      inputPane.add(dotProduct, 0, 1);

      // create pane for multiplying a matrix by a vector and put it in input pane
      GridPane matrixVector = createMatrixPane(true, graph, inputPane);
      inputPane.add(matrixVector, 0, 2);

      // create pane for multiplying a matrix by another matrix and put it in input pane
      GridPane matrixMatrix = createMatrixPane(false, graph, inputPane);
      inputPane.add(matrixMatrix, 0, 3);

      // create a border pane to store input pane (on left) and graph (on right)
      BorderPane main = new BorderPane();
      main.setPadding(new Insets(30, 30, 30, 30));
      main.setLeft(inputPane);
      main.setRight(graphPane);

      // place scene on stage, don't allow user to resize window, display stage
      stage.setScene(new Scene(main, 860, 500));
      stage.setResizable(false);
      stage.show();
    }
}
