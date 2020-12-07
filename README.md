# JBasicDashboard

## What?
JBasicDashboard is exactly what it says on the tin. It is a dashboard program, that is simple, and it is written in Java. JBasicDashboard features a grid-based ridgid canvas, minimalist and lightweight graphics, and a retro aesthetic. In addition, the following elements are available to be placed on the canvas:

- Text Labels
- Text Headers
- Dividers
- Images
- Buttons
- Sliders (Horizonal and Vertical)
- Status Icons
- Text Input Boxes
- Line Graphs
- Plots
- Loggers

## Why?
The primary reason why I wrote JBasicDashboard was that I got sick of writing Java Swing applications every time I wanted to do a simple graphical project. JBasicDashboard is made to be very simple to use, and to work with virtually no configuation. It takes one line of code to get a dashboard set up, and a similar amount to add additional elements.

## Why Not?
There is no shortage of actual dashboard or graphical libraries for Java. If you are writing an actual project, there are probably dozens of other libraries that does what this does, but better. This library is good for cobbling something together in an afternoon, but anything more professional should use something else.

## How?
The JAR for this library can be found in ```out/artifacts/JBasicDashboard_jar/JBasicDashboard.jar```. Simply add it to your project, and you are good to go. 

### Getting Started

The simple initialization of a Dashboard object will cause a new dashboard to be created onscreen. This dashboard needs to be configured with a type, name, width, height, and an optional icon. There are two types of dashboards currently, these being ```CONSOLE``` and ```GENERIC```. These dashboards are quite different from eachother, and will be explained separately.

### Console Dashboards

This type is as simple as it sounds. A console dashboard will act exclusivly as text I/O. Elements cannot be added to console dashboards. 

```
//Creates a console dashboard named "Console" with a width of 80 characters, and a height of 25
//A custom icon has been ommited
Dashboard console = new Dashboard(Dashboard.CONSOLE, "Console", 80, 25);

//Pretty standard println and print statements
console.println("Hello, World");
console.print("Hello Again\n);

//Scan a string in from the console, echos and is terminated when enter is pressed
String str = console.scanString();

//Scan in a single char, also echos
Character c = console.scanChar();

//If there is a char in the buffer, it is popped and returned. Otherwise 0 is returned
//Does not echo
Character g = console.getChar();
```

### Generic Dashboards

This type of dashboard consists of elements to be placed onto a predefined grid. Elements can be added, removed, or modified at any time. 

```
//Function to get image, just for the sake of example
Image img = exampleGetImage();

//Creates a generic dashboard named "Example Dashboard" with a width of 20 cells and a height of 20 cells
//Uses img as a custom icon
Dashboard dash = new Dashboard(Dashboard.GENERIC, "Example Dashboard", 20, 20, img);
```

After the dashboard object has been created, elements can be added to it

```
//It should be noted that this example only creates the element objects, and showcases different options for them
//To actually add an element to a dashboard, use dash.add(element);

//Creates a text label with the content "Hello, World" at location 0, 0
//The bounds of this text label is 10 wide by 1 high
//The text inside of the bounding is centered
JBTextLabel tl = new JBTextLabel("Hello, World", 0, 0, 10, 1, true);

//Enable drawing the bounding box
tl.setBoxed(true);

//Creates a text header with the content "Title" at location 0, 0
//The bounds of this text header is 12 wide by 3 high
//The text inside of the bounds is not centered, and has a size of 18
JBTextHeader th = new JBTextHeader("Title", 0, 0, 12, 3, false, 18);

//Creates a divider line spanning from 0,0 to 5,3
//Orientations for the beginning and end of the line in each cell must be defined
//These definitions can be MIDDLE, TOPLEFT, TOP, TOPRIGHT, RIGHT, BOTTOMRIGHT, BOTTOM, BOTTOMLEFT, and LEFT
JBLineDivider ld = new JBLineDivider(0, 0, Dashboard.TOPLEFT, 5, 3, Dashboard.BOTTOMRIGHT);

//Creates an image at 0, 0 with the bound of 8, 8
//The image source is the object img
Image img = exampleGetImage();
JBImage im = new IBImage(img, 0, 0, 8, 8);

//Creates a button that prints out "Hello, World" every time it is pressed
//This button is at 0, 0, and has bound of 8, 2
//The content of this button says "Hello"
//An ActionListener needs to be used to catch the button press
ActionListener action = new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent e) {
    console.println("Hello, World!");
  }
};

JBTextButton tb = new JBTextButton(action, "Hello", 0, 0, 8, 2);

//Creates a horizontal slider with at 0, 0, with a width of 10
//The process is the same vertical sliders, but with height swapped in
JBHorizontalSlider hs = new JBHorizontalSlider(0, 0, 10);

//The max and min values can be set as follows, default values are 0 and 1:
hs.setMin(-10);
hs.setMax(10);

//An ActionListener can be added to a slider to trigger when the slider is moved
hs.setEvent(action);

//Creates a small status icon at 0, 0
//States can be INACTIVE, NOMINAL, WARNING, ERROR, and NOTICE
JBStatusIcon si = new JBStatusIcon(Dashboard.NOMINAL, 0, 0);

//Creates a text input box at 0, 0, with a width of 10 and a height of 1
//Default text in the box is "Text Box!"
//No "On Enter" event will be defined
JBTextInput ti = new JBTextInput(null, "Text Box!", 0, 0, 10, 1);
```

The last three elements are all graph-related elements. Due to the fact that these elements are a little more involved, they will be gone over seperately.

```
//JBPlotter is the simplest of the graphs. It does not store a dataset, but simply plots what it is given over time.
//Creates a plot at 0, 0 with a bound of 24, 15
//The name of the plot is "Example Plot", and the labels for the X and Y axis are "X Axis" and "Y Axis"
JBPlotter plot = new JBPlotter("Example Plot", 0, 0, 24, 15, "X Axis", "Y Axis");

//The bounds of the graph can be set like this:
plot.setMinX(0);
plot.setMinY(0);
plot.setMaxX(10);
plot.setMaxY(10);
//The default values at 0, 0, 1, 1

//The plot can be cleared any time
plot.clearPlot();

//To plot a point, simply do:
plot.plot(7, 7);

//By default the plot will use disconenct markers for each plot, but these can be set to a continuous line if desired
plot.setLinear(true);

//The default color is green, but this can be set to anything
plot.setColor(Color.RED);

//To quickly plot multiple points, you can use fastplot
//Just make sure to update the plot after you are done
plot.fastPlot(0, 0)
plot.fastPlot(5, 5);
plot.fastPlot(10, 0);
plot.update();



//JBLineGraph is similar to JBPlotter, with the key different being that it pulls from provided datasets
//In addition, the output is always linear, and the contents of the datasets is automatically sorted when drawing the graph
//Creates a line graph at 0, 0 with the bounds of 24, 15
//The name of the graph is "Example Graph", and the labels for the X and Y axis are "X Axis" and "Y Axis"
JBLineGraph lg = new JBLineGraph("Example Graph", 0, 0, 24, 15, "X Axis", "Y Axis");

//Max and mins can be set just like the plot

//In order to get information up onto the graph, a dataset must be made and added to the graph
//This dataset will have the color green
Dataset d = new Dataset(Color.GREEN); 
lg.addDataset(d);

//Add points to the dataset, they do not need to be in order
d.add(new Point(0, 0));
d.add(new Point(1, 1));
d.add(new Point(0.5, 0.3));

//JBNumericLogger is about the same as a normal JBLineGraph, but with time on the X axis
//Values are tracking using the Tracker object
//Creates a logger at 0, 0, with the bounds 24, 15
//The name of the logger is "Example Logger", and the Y axis label is "Value"
//The duration of the tracker is 5 seconds
JBNumericLogger jb = new JBNumbericLogger("Example Logger", 0, 0, 24, 15, "Value", 5);

//Create tracker object with color blue
Tracker tr = new Tracker(0, Color.BLUE);

//Add the tracker
jb.addTracker(tr);

//This example will set up a JBHorizontalSlider, and track its value
JBHorizontalSlider ths = new JBHorizontalSlider(0, 0, 10)
hs.setEvent(new ActionListener() {
  @Override
  public void actionPerformed(ActionEvent e) {
    tr.setValue(((JBHorizontalSlider) e.getSource()).getValue());
  }
});
```
