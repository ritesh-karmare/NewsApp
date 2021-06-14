# NewsApp
=========

### Architecture

The architecture of this android project is implemented as MVVM (Model-View-ViewModel), where the Model are the Data classes, View are the Android View (Activity) files and layout files and the ViewModel is where the business logic is written, in this case, fetching the news from the server.

This projects uses the combination of MVVM + DataBinding + LiveData


### Core Version Used:

1. Networking - *Retrofit, OkHttp* <br />
2. JSON2Data - *GSON* <br />
3. ImageLoading - *Glide*

### Application Functionality/Features

1. Get all the news from News API("https://newsapi.org") and display the list of the news with image and title (headline)
2. OnClick of news item in the list, redirect to news detail screen.
3. Errors, in any case, are displayed via Toast.



#### Screen 1 - News List
<img src="https://raw.githubusercontent.com/ritesh-karmare/NewsApp-MAF/master/news_list.png" width="200" height="400" />

#### Screen 2 - News Details
<img src="https://raw.githubusercontent.com/ritesh-karmare/NewsApp-MAF/master/news_detail.png" width="200" height="400" />
