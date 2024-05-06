package edu.unh.cs.cs619.bulletzone.web;
import edu.unh.cs.cs619.bulletzone.model.entities.Dropship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import jakarta.servlet.http.HttpServletRequest;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.EntityDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.repository.GameRepository;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

@RestController
@RequestMapping(value = "/games")
class GamesController {

    private static final Logger log = LoggerFactory.getLogger(GamesController.class);

    private final GameRepository gameRepository;

    @Autowired
    public GamesController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(method=RequestMethod.POST, value="", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<LongWrapper> join(HttpServletRequest request) {
        try {
            String ip = request.getRemoteAddr();
            Dropship dropship = gameRepository.join(ip);
            long dropshipId = dropship.getId();
            long minerId = gameRepository.spawnMiner(dropshipId);
            long tankId = gameRepository.spawnTank(dropshipId);
            log.info("Player joined: dropshipId={} minerId={} tankId={} IP={}",
                    dropshipId, minerId, tankId, request.getRemoteAddr());

            return new ResponseEntity<LongWrapper>(
                    new LongWrapper(dropshipId, minerId, tankId),
                    HttpStatus.CREATED
            );
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (LimitExceededException | EntityDoesNotExistException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/move/{direction}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BooleanWrapper> move(@PathVariable long entityId, @PathVariable byte direction)
            throws EntityDoesNotExistException, LimitExceededException, IllegalTransitionException
    {
        boolean moved = gameRepository.move(entityId, Direction.fromByte(direction));
        BooleanWrapper response = new BooleanWrapper(moved);
        return new ResponseEntity<BooleanWrapper>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/moveTo/{targetX}/{targetY}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<Void> moveTo(@PathVariable long entityId,
                                @PathVariable int targetX, @PathVariable int targetY)
            throws EntityDoesNotExistException, InterruptedException {
        gameRepository.moveTo(entityId, targetX, targetY);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/fire/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BooleanWrapper> fire(@PathVariable long entityId)
            throws EntityDoesNotExistException, LimitExceededException
    {
        boolean fired = gameRepository.fire(entityId,1);
        BooleanWrapper response = new BooleanWrapper(fired);
        return new ResponseEntity<BooleanWrapper>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/fire/{bulletType}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BooleanWrapper> fire(@PathVariable long entityId, @PathVariable int bulletType)
            throws EntityDoesNotExistException, LimitExceededException
    {
        boolean fired = gameRepository.fire(entityId, bulletType);
        BooleanWrapper response = new BooleanWrapper(fired);
        return new ResponseEntity<BooleanWrapper>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/mine",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BooleanWrapper> mine(@PathVariable long entityId)
            throws EntityDoesNotExistException
    {
        gameRepository.mine(entityId);
        BooleanWrapper response = new BooleanWrapper(true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/dig",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BooleanWrapper> dig(@PathVariable long entityId)
            throws EntityDoesNotExistException
    {
        boolean tunneled = gameRepository.dig(entityId);
        BooleanWrapper response = new BooleanWrapper(tunneled);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{entityId}/ejectPowerUp",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<BooleanWrapper> ejectPowerUp(@PathVariable long entityId)
            throws EntityDoesNotExistException, LimitExceededException
    {
        boolean ejected = gameRepository.ejectPowerUp(entityId);
        BooleanWrapper response = new BooleanWrapper(ejected);
        return new ResponseEntity<BooleanWrapper>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{entityId}/leave",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    HttpStatus leave(@PathVariable long entityId) throws EntityDoesNotExistException {
        //System.out.println("Games Controller leave() called, tank ID: "+tankId);
        gameRepository.leave(entityId);
        return HttpStatus.OK;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleBadRequests(Exception e) {
        return e.getMessage();
    }

    // ------------------------ Spawn Endpoints ------------------------

    @RequestMapping(method = RequestMethod.PUT, value = "/{dropshipId}/spawn/tank",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<LongWrapper> spawnTank(@PathVariable long dropshipId)
            throws LimitExceededException, EntityDoesNotExistException {
        long tankId = gameRepository.spawnTank(dropshipId);
        LongWrapper response = new LongWrapper(tankId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{dropshipId}/spawn/miner",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<LongWrapper> spawnMiner(@PathVariable long dropshipId) throws EntityDoesNotExistException, LimitExceededException {
        long minerId = gameRepository.spawnMiner(dropshipId);
        LongWrapper response = new LongWrapper(minerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
