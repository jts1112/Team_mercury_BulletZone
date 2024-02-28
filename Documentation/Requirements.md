## Player
- turn tank token
- move tank token
- should be able to fire bullets fire bullets
- should be able to configure and save tank token (skin)
- should be able to login to the server
- should be able able to shake the device to fire
- should be able to see their account balance and items in their garage
## client
- has entities (Tank, wall or bullet)
- should keep track of account information
- should provide interface to user
- should show game state
- should support player login
- should store timestamped game state
- client should be able to replay saved game state at configurable speeds
## server
- should manage battlefield
- should manage game time
- should enforce rules
- should manage player accounts
- should keep track of entity properties (improvements, terrain types, and item)
- tank should be able to move once every half second
- tank should be able to fire once every half second
- tank should be able to fire only two bullets at a time
- tank should be able to make one 90 degree turn per step
- tank should be able to move forwards and backwards
- player should have a bank account
- bank should be able to store tanks and parts that the player acquires

# Use case diagram
![[Use_Case_Diagram.png]]

# Main success scenario
## Player Viewing Bank

### Preconditions

### Main Success Scenario
\1) User opens BulletZone app
\2) Client shows the user the login page
\3) User enters login information
\4) Client sends login request to server
\5) Server validates login information and returns a 200
\6) Client queries the server for the user's data
\7) Server retrieves user data from database
\8) Client closes login page and displays user's bank and garage information
\9) User reads bank information

### Extensions
5a) Server returns a 401
\1) Client tells user to try again
\2) Client clears the login data fields

### Postconditions
