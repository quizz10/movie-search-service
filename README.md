# Movie search service

This is a search service based on subsets of data from IMDB. It is built with Spring boot and a MongoDB database which is queried through GraphQL.

## Usage

```
Accessed at: localhost:8080/graphql (POST)
if you want to query through the web browser use localhost:8080/graphiql
```
```
What you can query for: find a movie and info about that movie
Create a simple user and see the ID like this:
mutation {newUser {userId}}
now that you have a user you can also query for a movie title and get genres stored which will be used for future recommendations:

query{
  findByOriginalTitleAndUserId(userId:17, originalTitle:"Se7en"){
    primaryTitle
    startYear
    averageRating
    genres
    names{
      primaryName
    }
  }
}
Now that your user has some genres stored you can find some recommendations!

query{
  findRecommendedTitlesByGenresAndUserId(userId:17){
    primaryTitle
    startYear
    averageRating
    genres
    names{
      primaryName
    }
  }
}

see schema.graphqls in resources folder for all supported types, queries and mutations.

```
## Installation

you need to create a mongodb database called moviedb

You need to download my imdb [datasets](https://drive.google.com/drive/u/1/folders/1TjCi1KuK36XYj7TjN4rTi59NuzYQ9pie)

to create these collections copy paste this into your terminal(for each collection):
Also note that you should keep the original names (for example moviecollection, namecollection)
```bash
mongoimport --db moviedb --collection [COLLECTION NAME] --type csv --headerline --file [FILENAME].csv 
```
Or you can take the full db [here](https://drive.google.com/file/d/1Gnd8w9NZI1nXLrJLlD_vfZHVn7kHfbKk/view?usp=sharing) and type this in your terminal
```bash
mongorestore --uri 'mongodb://localhost:27017/moviedb' --archive=[PATH TO FILE]
```
