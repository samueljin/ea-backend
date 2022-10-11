package com.ea.interview.assessment.controller;

import com.ea.interview.assessment.model.DriverStanding;
import com.ea.interview.assessment.model.RaceResults;
import com.ea.interview.assessment.service.F1SeasonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.parseInt;

@RestController
@RequestMapping("/api/f1")
public class F1SeasonController {

    private final F1SeasonService f1SeasonService;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    public F1SeasonController(F1SeasonService f1SeasonService) {
        this.f1SeasonService = f1SeasonService;
    }


    @GetMapping("{year}/standings")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<List<DriverStanding>> getDriverStandings(@PathVariable String year, @RequestParam(required = false) String sort) {

        try {
            logger.debug("Get driver standings for year:{}", year);
            if (sort == null) {
                return ResponseEntity.status(HttpStatus.OK).body(f1SeasonService.getDriveStandingsByYear(year));
            }

            List<DriverStanding> drStd = f1SeasonService.getDriveStandingsByYear(year);

            if (sort.equalsIgnoreCase("ASC")) {
                Collections.sort(drStd, new Comparator<DriverStanding>() {
                    @Override
                    public int compare(DriverStanding a1, DriverStanding a2) {
                        return parseInt(a1.getPosition()) - parseInt(a2.getPosition());
                    }
                });
            } else {
                Collections.sort(drStd, new Comparator<DriverStanding>() {
                    @Override
                    public int compare(DriverStanding a1, DriverStanding a2) {
                        return parseInt(a2.getPosition()) - parseInt(a1.getPosition());
                    }
                });
            }

            return ResponseEntity.status(HttpStatus.OK).body(drStd);

        } catch (Exception e) {
            logger.error("Controller Error getting driver standings for year {}.  Error message {{}}", year, e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("{year}/{round}/results")
    @CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<List<RaceResults>> getRaceResults(@PathVariable("year") String year, @PathVariable("round") String round, @RequestParam(required = false) String sort) {

        logger.debug("Get race result for year:{} and round {}", year, round);
        List<RaceResults> rr = f1SeasonService.getRaceResultsByYearRound(year, round);
        try {
            if (sort == null) {
                return ResponseEntity.status(HttpStatus.OK).body(rr);
            }

            if (sort.equalsIgnoreCase("ASC")) {
                Collections.sort(rr, new Comparator<RaceResults>() {
                    @Override
                    public int compare(RaceResults a1, RaceResults a2) {
                        return parseInt(a1.getPosition()) - parseInt(a2.getPosition());
                    }
                });
            } else {
                Collections.sort(rr, new Comparator<RaceResults>() {
                    @Override
                    public int compare(RaceResults a1, RaceResults a2) {
                        return parseInt(a2.getPosition()) - parseInt(a1.getPosition());
                    }
                });
            }

            return ResponseEntity.status(HttpStatus.OK).body(rr);


        } catch (Exception e) {
            logger.error("Controller Error getting race result for year:{} and round {}.  Error message {{}}", year, round, e.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    //add data for other season

}
