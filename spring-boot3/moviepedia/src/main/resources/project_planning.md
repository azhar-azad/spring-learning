### Entities
* Role
* User
* Movie
* Director
* Writer
* Cast
* People
* Review
* Genre

### Properties & Relationships
* Role
  * id (PK)
  * roleName (unique)
- User
  - id (PK)
  - email (unique)
  - password
  - totalReviews
  - totalRatings
  - avgRatingGiven
  - role_id (FK: Role)
* Genre
   * id (PK)
   * name (unique -> managed by enum)
   * movieCount (how many movies have this genre)
- Director/Writer
   - id (PK)
   - firstName
   - lastName
   - birthDate
   - birthPlace
   - age
   - miniBio
* Cast
  * id (PK)
  * firstName
  * lastName
  * birthDate
  * birthPlace
  * age
  * miniBio
- MovieCast
  - movieId (FK -> Movie)
  - castId (FK -> Cast)
  - character 
  - ** Implement awards **
* People
  * id (PK)
  * movieRole (unique -> managed by enum)
  * firstName
  * lastName
  * birthDate
  * birthPlace
  * age
  * miniBio
- Movie
  - id (PK)
  - title 
  - year
  - pgRating
  - runtime
  - plot
  - rating
  - totalVotes
* Review
  * id (PK)
  * title
  * description