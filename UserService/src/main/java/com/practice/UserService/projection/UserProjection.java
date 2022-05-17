package com.practice.UserService.projection;

import com.practice.CommonService.models.CardDetails;
import com.practice.CommonService.models.User;
import com.practice.CommonService.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query){
        //Ideally from DB
        CardDetails cardDetails = CardDetails.builder()
                .name("Prashant Sharma")
                .validUntilMonth(01)
                .validUntilYear(2023)
                .cvv(121)
                .cardNumber("1234567891234").build();

        return User.builder()
                .userId(query.getUserId())
                .firstName("Prashant")
                .lastName("Sharma")
                .cardDetails(cardDetails)
                .build();
    }
}
