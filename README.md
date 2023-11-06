<h1>Sales-based Order Statistics</h1>

<h2>Overview</h2>

The proposed project aims to analyze sales data on the Dott platform to determine if older products are still being sold. This analysis is conducted by gathering information on orders, items, and products. Each order contains general data such as customer information and order date. An order can contain multiple items, and each item is associated with a product. </br></br>

The main task is to develop a tool that, upon receiving a date range, filters the orders made during that period and groups them based on the age of the products, determined by the date they were created. This allows evaluation of whether older products have demand by showing the number of orders made for products of different age ranges. </br></br>

Additionally, the project proposes the ability to customize time intervals for analysis, rather than following pre-defined fixed intervals. This analysis is crucial for making decisions regarding inventory, marketing, and sales strategies.

<h2>Features</h2>

+ Search products based on the specified time.
+ Display the number of orders made during the chosen period.
+ Filter products using custom time intervals.


<h2>Technologies</h2>

+ Scala
+ Scalatest
+ JDBC
+ Slick
+ PostgreSQL
+ pgAdmin 4

</br>

<h2>Installation</h2>

<h4>Installing Java</h4>

```bash
sudo apt-get update
sudo apt-get install default-jdk
```


<h4>Installing Scala</h4>

```bash
sudo apt-get install scala
```

<strong>Version used in the project: Scala 2.13.12</strong>


<h2>Dependencies</h2></br>

```sbt
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.postgresql" % "postgresql" % "42.3.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "com.github.tminglei" %% "slick-pg" % "0.20.3",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.20.3",
  "org.scalatest" %% "scalatest" % "3.2.17" % Test
)
```

<h2>How to Use</h2></br>
1. Run the command: <strong>sbt compile</strong> </br></br>
2. Example of usage: <strong>sbt "run "2020-10-21T14:30:00" "2023-09-21T14:30:00" "</strong>
</br>

<h2>Update History</h2>

<h4>Version 1.0</h4>

+ Creation of classes, objects, and logic for handling orders. ✅
+ Initial layout of the main interface. ✅
+ Development of generators for orders, items, and products. ✅
  
<h4>Version 2.0</h4>

+ Implementation of a database (PostgreSQL). ✅
+ Use of JDBC for database connection. ✅
+ Removal of generators. ✅
  
<h4>Version 3.0</h4>

+ Transition from JDBC to Slick. ✅
+ Minor error adjustments. ✅
+ Creation of tests.✅

<h2>Author</h2>

+ Gustavo Sousa Castro
+ Email: gust4v0309@gmail
+ LinkedIn: [Gustavo's LinkedIn](https://www.linkedin.com/in/gustavos-castro/)

