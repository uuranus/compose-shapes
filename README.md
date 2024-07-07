# Compose Shapes
Various shapes drawn with Jetpack Compose

![compose shapes thumbnail](https://github.com/uuranus/compose-shapes/assets/72340294/56357d3c-ad0a-4f15-94b0-f6ca07cc0082)
# Rectangles

<table>

  <th>Name</th>
  <th>Screenshot</th>
  <th>Code</th>

  <tr>
    <td>Parallelogram</td>
    <td><img src = "https://github.com/uuranus/compose-shapes/assets/72340294/a8a30f67-46e9-4c8d-a641-3cd243363d61" width = "300"></td>
<td>
      
```kotlin

ParallelogramShape(
  skewed = 0.2f,
  modifier = Modifier.fillMaxSize()
)

```
</td>
  </tr>

<tr>
    <td>Rhombus</td>
    <td><img src = "https://github.com/uuranus/compose-shapes/assets/72340294/9d00e04e-ab4e-4e37-aba8-3fc77fe36471" width = "300"></td>
<td>
      
```kotlin

RhombusShape(
  modifier = Modifier.fillMaxSize()
)

```

</td>
  </tr>

  <tr>
    <td>Trapezoid</td>
    <td><img src = "https://github.com/uuranus/compose-shapes/assets/72340294/c8ca4b1d-3a8b-45f6-9877-710398a95929" width = "300"></td>
<td>
      
```kotlin

TrapezoidShape(
  leftSkewed = 0.2f,
  rightSkewed = 0.2f,
  modifier = Modifier.fillMaxSize()
)

```

</td>
  </tr>
</table>


# Polygons

<table>
  <th>Name</th>
  <th>Screenshot</th>
  <th>Code</th>

  <tr>
    <td>Regular Polygons</td>
    <td><img src = "https://github.com/uuranus/compose-shapes/assets/72340294/99d054ab-d0f9-4040-8847-0e2fbf0b06ae" width = "300"></td>
<td>
      
```kotlin

PolygonShape(
  numOfPoints = 5,
  modifier = Modifier.fillMaxWidth(),
)

```
</td>

</tr>
</table>

# Stars

<table>
  <th>Name</th>
  <th>Screenshot</th>
  <th>Code</th>

  <tr>
    <td>Star</td>
    <td><img src = "https://github.com/uuranus/compose-shapes/assets/72340294/b2efa930-6365-4bd3-8220-e4941fe08eb8" width = "300"></td>
<td>
      
```kotlin

StarShape(
  innerRadiusRatio = 0.5f,
  numOfPoints = 5,
  modifier = Modifier
    .fillMaxWidth()
)

```
</td>

</tr>
</table>


# Get Started
Step 1. Add it in your root build.gradle at the end of repositories
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
```

Step 2. Add the dependency
```kotlin
dependencies {
	  implementation("com.github.uuranus:compose-shapes:latest-version")
}
```
