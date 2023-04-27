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
mutation {newUser {userID}}
now that you have a user you can also query for a movie title and get genres stored which will be used for future recommendations:

query{
  findByOriginalTitle(userId:17, originalTitle:"Se7en"){
    primaryTitle
  }
}
Now that your user has some genres stored you can find some recommendations!

query{
  findRecommendedTitlesByGenres(userId:17){
    primaryTitle
  }
}

see schema.graphqls in resources folder for all supported types, queries and mutations.

```
## Installation

you need to create a mongodb database called moviedb

You need to download my imdb [datasets](https://drive.google.com/drive/u/1/folders/1TjCi1KuK36XYj7TjN4rTi59NuzYQ9pie)

to create these collections copy paste this into your terminal(for each collection):

```bash
mongoimport --db moviedb --collection [COLLECTION NAME] --type csv --headerline --file [FILENAME].csv 
```
