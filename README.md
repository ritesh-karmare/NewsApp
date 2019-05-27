# NewsApp
=======

### Application Functionality/Features

1. Get all the news from News API("https://newsapi.org") and display the list of the news with image and title (headline)
2. OnClick of news item in the list, redirect to news detail screen.
3. Errors, in any case, are displayed via Toast.



#### Screen 1 - News List
<img src="https://raw.githubusercontent.com/ritesh-karmare/NewsApp-MAF/master/news_list.png" width="200" height="400" />


#### Screen 2 - News Details
<img src="https://raw.githubusercontent.com/ritesh-karmare/NewsApp-MAF/master/news_detail.png" width="200" height="400" />

### Architecture

The architecture of this android project is implemented as MVP (Model-View-Presenter), where the Model is the Data classes, View are the Android View (Activity) files and layout files and the Presenter is where the business logic is written, in our case, fetching the news from the server.

**Working:**

From View the Presenter is initialised and we call the function in the Presenter to fetch the data from server, which in return sets the data in the Model data classes and return the Model to the View.


### Core Deps Used:

1. Networking - *Retrofit, OkHttp* <br />
2. JSON2Data - *GSON* <br />
3. ImageLoading - *Glide*
