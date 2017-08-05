package fr.altoine.instatrain.models.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Traffic - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */

/**
 * Sole purpose of this class it to mimic API JSON response
 */
public class Traffic {
    @SerializedName("result")
    private Result result;

    class Result {
        @SerializedName("metros")
        private List<Metro> metros;

        @SerializedName("rers")
        private List<Rer> rers;

        @SerializedName("tramways")
        private List<Tramway> tramways;

        class Transports {
            @SerializedName("line")
            private String line;

            @SerializedName("slug")
            private String slug;

            @SerializedName("title")
            private String title;

            @SerializedName("message")
            private String message;

            public String getLine() {
                return line;
            }

            public String getSlug() {
                return slug;
            }

            public String getTitle() {
                return title;
            }

            public String getMessage() {
                return message;
            }
        }

        class Metro extends Transports {
            @Override
            public String toString() {
                return "Metro " + super.getLine() + ": " + super.getSlug();
            }
        }

        class Rer extends Transports {
            @Override
            public String toString() {
                return "Rer " + super.getLine() + ": " + super.getSlug();
            }
        }
        class Tramway extends Transports {
            @Override
            public String toString() {
                return "Tramway " + super.getLine() + ": " + super.getSlug();
            }
        }
    }
}
