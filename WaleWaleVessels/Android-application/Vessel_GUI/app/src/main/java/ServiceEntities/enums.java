package ServiceEntities;

/**
 * Created by maxedman on 2017-04-21.
 */
// Is not in use at the moment because of problem with casting Json-string ti Java-enumerator.
    enum TimeType {
        RECOMMENDED, TARGET, CANCELLED, ESTIMATED, ACTUAL
    }

    enum ServiceTimeSequence {
        REQUESTED, REQUEST_RECIEVED, CONFIRMED, DENIED, COMMENCED, COMPLETED
    }

    enum LocationTimeSequence {
        ARRIVAL_TO, DEPAPRTURE_FROM
    }

    enum ReferenceObject {
        AGENT, ARRIVAL_MOORER, DEPARTURE_MOORER, ESCORT_TUG, PASSENGER, PILOT, PILOT_BOAT,
        TUG, VESSEL, ICEBREAKER, SECURITY, PONTOONS_AND_FENDERS
    }

    enum LocationType {
        ANCHORING_AREA, BERTH, ETUG_ZONE, LOC, PILOT_BOARDING_AREA, RENDEZ_AREA, TRAFFIC_AREA, TUG_ZONE,
        VESSEL
    }

    enum ServiceObject {
        //TODO Insert all ServiceObjects from PDF-slide. There's a lot of them...
    }
