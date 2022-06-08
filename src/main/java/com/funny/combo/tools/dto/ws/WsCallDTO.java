package com.funny.combo.tools.dto.ws;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class WsCallDTO implements Serializable {
    private String cmd;
}
