# Compose Shapes
Various shapes drawn with Jetpack Compose

![compose shapes thumbnail](https://github.com/uuranus/compose-shapes/assets/72340294/56357d3c-ad0a-4f15-94b0-f6ca07cc0082)
# Quadrilateral

<table>

  <th>Name</th>
  <th>Screenshot</th>
  <th>Code</th>
  
  <tr>
    <td>Rectangle</td>
    <td><img src = "https://github.com/user-attachments/assets/15a432e6-47a6-4c78-8cad-4e542e91c945" width = "250"></td>
<td>

```kotlin

Box(
    modifier = Modifier
        .fillMaxSize()
        .weight(0.3f)
        .background(
            color = Color.Gray,
            shape = RectangleShape(
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            )
        )
) {
    // Content goes here
}

```
</td>
  </tr>

  <tr>
    <td>Parallelogram</td>
    <td><img src = "https://github.com/user-attachments/assets/5c445573-09b1-4050-bfdf-15917061944c" width = "250"></td>
<td>

```kotlin

Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(
            Color.Gray,
	    shape = ParallelogramShape(
	        skewed = 0.3f,
    		topStart = 24.dp,
   	 	bottomEnd = 24.dp,
    		topEnd = 12.dp,
    		bottomStart = 12.dp
	    )
        )
) {
    // Content goes here
}

```
</td>
  </tr>

<tr>
    <td>Rhombus</td>
    <td><img src = "https://github.com/user-attachments/assets/23fe6611-de19-4318-9030-fb1ced2abc0f" width = "250"></td>
<td>

```kotlin

Box(
    modifier = Modifier
        .fillMaxSize()
        .weight(0.5f)
        .background(
            Color.Gray,
            shape = RhombusShape(
                top = 24.dp,
                start = 12.dp,
                end = 12.dp,
                bottom = 24.dp
            )
        )
) {
}

```

</td>
  </tr>

  <tr>
    <td>Trapezoid</td>
    <td><img src = "https://github.com/user-attachments/assets/9f7b62ef-fe0c-4cae-a715-53417e1bf697" width = "250"></td>
<td>

```kotlin

Box(
    modifier = Modifier
        .fillMaxSize()
        .weight(0.5f)
        .background(
            Color.Gray,
            shape = TrapezoidShape(
                startSkewed = 0.2f,
                endSkewed = 0.4f,
                topStart = 12.dp,
                topEnd = 12.dp,
                bottomStart = 12.dp,
                bottomEnd = 12.dp
            )
        )
) {
}

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
    <td><img src = "https://github.com/uuranus/compose-shapes/assets/72340294/99d054ab-d0f9-4040-8847-0e2fbf0b06ae" width = "250"></td>
<td>
      
```kotlin

Box(
    modifier = Modifier
        .fillMaxWidth()
        .background(
            color = Color.Gray,
            shape = PolygonShape(
                numOfPoints = 5
            )
        )
) {
    // Content goes here
}

```
</td>

</tr>
<tr>
    <td>Star Polygon</td>
    <td><img src = "https://github.com/user-attachments/assets/edc18908-705b-4334-b252-ad0dd6252f1a" width = "250"></td>
<td>

```kotlin

Box(
    modifier = Modifier
        .fillMaxSize()
        .weight(0.5f)
        .background(
            Color.Gray,
            shape = StarPolygonShape(
                numOfPoints = 5,
                innerRadiusRatio = 0.5f,
                outerCornerSize = 24.dp,
                innerCornerSize = 12.dp
            )
        )
) {
}

```
</td>

</tr>
</table>

# CornerStyle
You can customize corners using the CornerStyle enum class.

<table>
  <th>Rounded</th>
  <th>Inner Rounded</th>
  <th>Cut</th>

  <tr>
    <td><img src = "https://github.com/user-attachments/assets/aec6cb51-2bce-4d26-8a4f-c4f522891475" width = "250"></td>
    <td><img src = "https://github.com/user-attachments/assets/03ff4b40-ee07-4ea6-ba55-e0fa968ceb30" width = "250"></td>
    <td><img src = "https://github.com/user-attachments/assets/377b0578-bb4d-4e73-8a1c-111a80e92232" width = "250"></td>

</tr>

</table>

# Others

<table>
  <th>Cloud</th>
  <th>Ring</th>

  <tr>
    <td><img src = "https://github.com/user-attachments/assets/10875742-2277-45e5-b2a8-2dc3787a10c2" width = "250"></td>
    <td><img src = "https://github.com/user-attachments/assets/dc5aea6f-69e2-4de8-8f73-16d0a6ebf227" width = "250"></td>

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


# LICENSE
```
Copyright 2024 uuranus All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
