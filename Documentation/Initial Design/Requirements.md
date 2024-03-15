## Player
- Player should be able to turn tank token.
- Player should be able to move tank token.
- Player should be able to fire bullets fire bullets.
- Player should be able to configure and save tank token (skin).
- Player should be able to login to the server.
- Player should be able able to shake the device to fire.
- Player should be able to see their account balance and items in their garage.
## client
- Client should have entities (Tank, wall or bullet).
- Client should keep track of account information.Join
- Client should provide interface to user.
- Client should show game state.
- Client should support player login.
- Client should store timestamped game state.
- Client should be able to replay saved game state at configurable speeds.
## server
- Server should manage battlefield.
- Server should manage game time.
- Server should enforce rules.
- Server should manage player accounts.
- Server should keep track of entity properties (improvements, terrain types, and item).
- Server should allow tank to move once every half second.
- Server should allow tank to fire once every half second.
- Server should allow tank fire only two bullets at a time.
- Server should allow tank to make one 90 degree turn per step.
- Server should allow tank to move forwards and backwards.
- Server keep track of the player's bank account.
- Server should ensure bank can store tanks and parts that the player acquires.

# Use case diagram
![[Use_Case_Diagram.png]]

# Main success scenario
## Player Viewing Bank

### Preconditions
N/A

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
N/A