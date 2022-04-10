package pl.artur.zaczek.car.mechanic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BodyType {
    COUPE("nadwozie charakteryzujące się opływową sylwetką"),
    CONVERTIBLE("samochód ze składanym dachem"),
    CROSSOVER("duży samochód łączący w sobie cechy kilku klas samochodów"),
    ELECTRIC("electric car - samochód elektryczny posiadający wyłącznie elektryczny silnik"),
    COMBI ("samochód typu kombi – o wydłużonej tylnej części"),
    FASTBACK("nadwozie, w którym tylna część samochodu opada pod niewielkim kątem"),
    HATCHBACK("hatchback - nadwozie, w którym tył samochodu jest „ścięty”"),
    HYBRID_CAR("samochód hybrydowy z silnikiem spalinowym i elektrycznym"),
    LIMOUSINE("limousine - wydłużony, luksusowy samochód z bogatym wyposażeniem typu barek, telefon itp."),
    PICKUP("pickup - nadwozie dostawcze ze skrzynią ładunkową"),
    SEDAN("sedan - samochód z dużym nadwoziem oraz wysuniętym przodem i tyłem"),
    SPORTS_CAT("sports car - samochód sportowy o podwyższonej mocy silnika i opływowym nadwoziu"),
    SUV("samochód łączący cechy samochodu terenowego i osobowego"),
    VAN("rodzaj samochodu wielozadaniowego o dużej bryle i przestrzeni załadunkowej/z siedzeniami");

    private String description;
}
