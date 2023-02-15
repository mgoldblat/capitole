package com.capitole.exam.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.capitole.exam.domain.Price;
import com.capitole.exam.dto.PriceDto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

class PriceControllerTests extends ControllerTest {

  private static Stream<Arguments> searchPriceSucceedArgs() {
    return Stream.of(
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-14T10:00:00"))
                .build(),
            Price.builder()
                .productId(35455)
                .brandId(1)
                .priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .price(BigDecimal.valueOf(35.5))
                .build()),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-14T16:00:00"))
                .build(),
            Price.builder()
                .productId(35455)
                .brandId(1)
                .priceList(2)
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                .price(BigDecimal.valueOf(25.45))
                .build()),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-14T21:00:00"))
                .build(),
            Price.builder()
                .productId(35455)
                .brandId(1)
                .priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .price(BigDecimal.valueOf(35.5))
                .build()),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-15T10:00:00"))
                .build(),
            Price.builder()
                .productId(35455)
                .brandId(1)
                .priceList(3)
                .startDate(LocalDateTime.parse("2020-06-15T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-15T11:00:00"))
                .price(BigDecimal.valueOf(30.50))
                .build()),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-16T21:00:00"))
                .build(),
            Price.builder()
                .productId(35455)
                .brandId(1)
                .priceList(4)
                .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .price(BigDecimal.valueOf(38.95))
                .build()));
  }

  private static Stream<Arguments> searchPriceFailsArgs() {
    return Stream.of(
        Arguments.arguments(
            PriceDto.builder()
                .productId(35455)
                .dateTime(LocalDateTime.parse("2020-06-14T10:00:00"))
                .build(),
            HttpStatus.UNPROCESSABLE_ENTITY,
            "brand_id_required"),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .dateTime(LocalDateTime.parse("2020-06-14T16:00:00"))
                .build(),
            HttpStatus.UNPROCESSABLE_ENTITY,
            "product_id_required"),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .build(),
            HttpStatus.UNPROCESSABLE_ENTITY,
            "date_time_required"),
        Arguments.arguments(
            PriceDto.builder()
                .brandId(1)
                .productId(35455)
                .dateTime(LocalDateTime.parse("1900-01-01T00:00:00"))
                .build(),
            HttpStatus.NOT_FOUND,
            "price_not_found"));
  }

  @ParameterizedTest
  @MethodSource("searchPriceSucceedArgs")
  public void searchPriceShouldReturnTheRightPrice(PriceDto dto, Price expected) {
    Price result = post("/prices/search", dto)
        .statusCode(HttpStatus.OK.value())
        .extract()
        .as(Price.class);

    assertThat(result.getProductId(), equalTo(expected.getProductId()));
    assertThat(result.getBrandId(), equalTo(expected.getBrandId()));
    assertThat(result.getPriceList(), equalTo(expected.getPriceList()));
    assertThat(result.getStartDate(), equalTo(expected.getStartDate()));
    assertThat(result.getEndDate(), equalTo(expected.getEndDate()));
    assertThat(result.getPrice(), equalTo(expected.getPrice()));
  }

  @ParameterizedTest
  @MethodSource("searchPriceFailsArgs")
  public void searchPriceShouldFail(PriceDto dto, HttpStatus responseStatus, String error) {
    post("/prices/search", dto)
        .statusCode(responseStatus.value())
        .body("error", equalTo(error));
  }
}