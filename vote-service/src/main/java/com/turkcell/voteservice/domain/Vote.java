package com.turkcell.voteservice.domain;

import java.time.Instant;
import java.util.UUID;

public class Vote {

    private UUID voteId;
    private UUID surveyId;
    private UUID questionId;
    private UUID optionId;
    private UUID userId;
    private Instant voteTime;




    //user aynı questiona 1 kez vote verebilir
    //survey açık olmalıdır.
    //kullanıcı bir question için bir tane option secebilir.
}
