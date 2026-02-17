package com.oscaresteve.rentboot.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

public final class PaginationHelper {

  private PaginationHelper() {
  }

  public static Pageable createPageable(int page, int size, String[] sort) {
    List<Order> criteriosOrdenacion = new ArrayList<>();

    if (sort == null || sort.length == 0) {
      criteriosOrdenacion.add(new Order(Direction.ASC, "id"));
      return PageRequest.of(page, size, Sort.by(criteriosOrdenacion));
    }

    if (sort[0].contains(",")) {
      for (String criterioOrdenacion : sort) {
        String[] orden = criterioOrdenacion.split(",");
        String field = orden[0].trim();
        Direction direction = orden.length > 1 ? Direction.fromString(orden[1].trim()) : Direction.ASC;
        criteriosOrdenacion.add(new Order(direction, field));
      }
    } else {
      String field = sort[0].trim();
      Direction direction = sort.length > 1 ? Direction.fromString(sort[1].trim()) : Direction.ASC;
      criteriosOrdenacion.add(new Order(direction, field));
    }

    return PageRequest.of(page, size, Sort.by(criteriosOrdenacion));
  }
}
