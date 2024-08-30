package user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* @Data is a convenient shortcut annotation that bundles the features of
@ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor
together:*/
@Data
/*The @Builder annotation is part of Project Lombok, a library that reduces
boilerplate code in Java. Project Lombok achieves this by
generating common code constructs, such as getters, setters,
constructors, and builders, during compile time. The @Builder
annotation, specifically, generates a builder pattern for a class,
allowing you to create instances of the class with a fluent and expressive API.*/
@Builder
// @NoArgsConstructor will generate a constructor with no parameters.
@NoArgsConstructor
/*@AllArgsConstructor generates a constructor with 1 parameter for
each field in your class. Fields marked with @NonNull result in
null checks on those parameters. */
@AllArgsConstructor
public class User {
    private Integer id;
    private String firstName;
    private String lastName;

    private String email;
    private String password;

}
