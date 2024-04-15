package edu.unh.cs.cs619.bulletzone.rest;

import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.annotations.RestService.*;
import org.androidannotations.rest.spring.api.RestClientErrorHandling;
import org.androidannotations.rest.spring.api.RestClientHeaders.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;

import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.GameEventCollectionWrapper;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;
import edu.unh.cs.cs619.bulletzone.util.ResultWrapper;

/** "http://stman1.cs.unh.edu:6191/games"
 * "http://10.0.0.145:6191/games"
 * http://10.0.2.2:8080/
 * Created by simon on 10/1/14.
 */

//@Rest(rootUrl = "http://172.29.160.1:8080/games",
@Rest(rootUrl = "http://172.16.32.92:8080/games", // Shane's ip
//@Rest(rootUrl = "http://10.21.173.197:8080/games",
//@Rest(rootUrl = "http://10.21.164.194:8080/games",
//@Rest(rootUrl = "http://stman1.cs.unh.edu:61905/games",
//@Rest(rootUrl = "http://stman1.cs.unh.edu:6192/games",
//@Rest(rootUrl = "http://stman1.cs.unh.edu:61902/games",
        converters = {StringHttpMessageConverter.class, MappingJackson2HttpMessageConverter.class}
        // TODO: disable intercepting and logging
        // , interceptors = { HttpLoggerInterceptor.class }
)
public interface BulletZoneRestClient extends RestClientErrorHandling {
    void setRootUrl(String rootUrl);

    @Post("")
    LongWrapper join() throws RestClientException;

    @Get("")
    GridWrapper grid();

    @Get("/events/{sinceTime}")
    GameEventCollectionWrapper events(@Path long sinceTime);

    @Put("/account/register/{username}/{password}")
    BooleanWrapper register(@Path String username, @Path String password);

    @Put("/account/login/{username}/{password}")
    LongWrapper login(@Path String username, @Path String password);

    @Put("/{entityId}/move/{direction}")
    BooleanWrapper move(@Path long entityId, @Path byte direction);

    @Put("/{entityId}/turn/{direction}")
    BooleanWrapper turn(@Path long entityId, @Path byte direction);

    @Put("/{entityId}/fire/1")
    BooleanWrapper fire(@Path long entityId);

    @Put("/{entityId}/fire/{bulletType}")
    BooleanWrapper fire(@Path long entityId, @Path byte bulletType);

    @Delete("/{entityId}/leave")
    BooleanWrapper leave(@Path long entityId);

    // ------------ Spawn Endpoints ------------
    @Put("/{dropshipId}/spawn/miner")
    LongWrapper spawnMiner(@Path long dropshipId);

    @Put("/{dropshipId}/spawn/tank")
    LongWrapper spawnTank(@Path long dropshipId);

}
