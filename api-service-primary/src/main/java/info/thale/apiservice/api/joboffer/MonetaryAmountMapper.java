package info.thale.apiservice.api;

import java.math.BigDecimal;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import info.thale.apiservice.api.generated.model.MoneyDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MonetaryAmountMapper {

    static MonetaryAmount toMoney(MoneyDTO moneyDTO) {
        return Money.of(BigDecimal.valueOf(moneyDTO.getAmount()), Monetary.getCurrency(moneyDTO.getCurrency()));
    }

}
