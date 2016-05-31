# New York Times Article Search

New York Times News Search article app allows a user to search and read old articles.

Submitted by: Shankar Sundaram

Time spent: 14 hours spent in total

## User Stories

The following **required** functionality is completed:

* User can enter a search query that will display a grid of news articles using the thumbnail and headline from the New York Times Search API.
* User can click on "settings" which allows selection of advanced search options to filter results.
* User can configure advanced search filters such as:
  * Begin Date (using a date picker)
  * News desk values (Arts, Fashion & Style, Sports)
  * Sort order (oldest or newest)
* Subsequent searches will have any filters applied to the search results.
* User can tap on any article in results to view the contents in an embedded browser
* User can scroll down "infinitely" to continue loading more news articles. The maximum number of articles is limited by the API search

The following **optional** features are implemented:

* Advanced: Robust error handling, check if internet is available, handle error cases, network failures.
* Advanced: Use the ActionBar SearchView or custom layout as the query box instead of an EditText
* Advanced: User can share a link to their friends or email it to themselves
* Advanced: Improve the user interface and experiment with image assets and/or styling and coloring
* Bonus: Use the RecyclerView with the StaggeredGridLayoutManager to display improve the grid of image results
* Bonus: For different news articles that only have text or have text with thumbnails, use Heterogenous Layouts with RecyclerView
* Bonus: Apply the popular ButterKnife annotation library to reduce view boilerplate
* Bonus: Use Parcelable instead of Serializable using the popular Parceler library.
* Bonus: Leverage the popular GSON library to streamline the parsing of JSON data

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='Demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes

* Implemented multiple date picker dialogs (for begin and end dates)
* Show progress bars when loading more data and hide when data is loaded

## License

    Copyright [2016] [Shankar Sundaram]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

