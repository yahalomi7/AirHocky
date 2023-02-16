package com.multiplayer;

import com.company.Paddle;

public class DataClient {
    Integer yPaddle;

    public DataClient(Paddle paddle) {
        this.yPaddle = paddle.y;
    }
}
