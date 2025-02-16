package com.somuncu.footballmarkt.core.utiliites.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.ui.Model;

public interface ModelMapperService {
    public ModelMapper forResponse();
    public ModelMapper forRequest();
}
