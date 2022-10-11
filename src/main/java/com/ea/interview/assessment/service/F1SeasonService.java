package com.ea.interview.assessment.service;

import com.ea.interview.assessment.model.DriverStanding;
import com.ea.interview.assessment.model.RaceResults;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class F1SeasonService {

    private final RestTemplate restTemplate;
    @Value("${STANDINGS.URL}")
    String DRIVER_STANDINGS_URL;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    @Value("${RESULTS.URL}")
    private String RACE_URL;

    public F1SeasonService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DriverStanding> getDriveStandingsByYear(String year) {

        logger.debug("get DriveStandings By {}", year);
        JSONObject jsonResponse = getJSONResponse(DRIVER_STANDINGS_URL);
        List<DriverStanding> driverStandings = new ArrayList<>();

        JSONArray standingsList = jsonResponse.getJSONObject("MRData").getJSONObject("StandingsTable").getJSONArray("StandingsLists");

        if (standingsList != null) {
            ObjectMapper mapper = new ObjectMapper();

            for (int i = 0; i < standingsList.length(); i++) {
                String season = standingsList.getJSONObject(i).getString("season");
                if (season.equals(year)) {
                    JSONArray dsArr = standingsList.getJSONObject(i).getJSONArray("DriverStandings");
                    // convert to  DriverStanding array
                    try {
                        DriverStanding[] driverStandingsArray = mapper.readValue(dsArr.toString(), DriverStanding[].class);

                        driverStandings.addAll(Arrays.asList(driverStandingsArray));
                    } catch (IOException e) {
                        logger.error("Error in convert json to java object {}", e.getLocalizedMessage());
                    }
                }
            }
        }

        return driverStandings;
    }

    public List<RaceResults> getRaceResultsByYearRound(String year, String round) {
        logger.debug("get reace results by year {] and round {}", year, round);
        JSONObject jsonResponse = getJSONResponse(RACE_URL);
        JSONArray jsonArray = jsonResponse.getJSONObject("MRData").getJSONObject("RaceTable").getJSONArray("Races");

        List<RaceResults> raceResults = new ArrayList<>();

        if (jsonArray != null) {
            ObjectMapper mapper = new ObjectMapper();

            for (int i = 0; i < jsonArray.length(); i++) {
                String season = jsonArray.getJSONObject(i).getString("season");
                String raceRound = jsonArray.getJSONObject(i).getString("round");

                if (season.equals(year) && raceRound.equals(round)) {
                    JSONArray resultsArr = jsonArray.getJSONObject(i).getJSONArray("Results");

                    if (resultsArr != null) {
                        try {
                            RaceResults[] results = mapper.readValue(resultsArr.toString(), RaceResults[].class);
                            raceResults.addAll(Arrays.asList(results));
                        } catch (IOException e) {
                            logger.error("Error in convert json to java object {}", e.getLocalizedMessage());
                        }
                    }
                }
            }
        }

        return raceResults;

    }

    private JSONObject getJSONResponse(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return new JSONObject(response.getBody());
    }
}
