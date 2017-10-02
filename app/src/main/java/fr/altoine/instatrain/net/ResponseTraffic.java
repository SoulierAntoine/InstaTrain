package fr.altoine.instatrain.net;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * ResponseTraffic - InstaTrain
 * Created by soulierantoine on 03/08/2017
 */

/*
 * Sole purpose of this class it to mimic API JSON response
 */
public class ResponseTraffic {
    @SerializedName("result")
    private Result result;

    @Nullable
    private String message;

    public Result getResult() {
        return result;
    }

    public class Result {
        @SerializedName("metros")
        private List<Metro> metros;

        @SerializedName("rers")
        private List<Rer> rers;

        @SerializedName("tramways")
        private List<Tramway> tramways;

        public List<Transports> getTransports() {
            List<Transports> transports = new ArrayList<>();
            transports.addAll(getMetros());
            transports.addAll(getRers());
            transports.addAll(getTramways());
            return transports;
        }

        public List<Metro> getMetros() {
            return metros;
        }

        public List<Rer> getRers() {
            return rers;
        }

        public List<Tramway> getTramways() {
            return tramways;
        }

        public class Transports {
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

        public class Metro extends Transports {
            @Override
            public String toString() {
                return "Metro " + super.getLine() + ": " + super.getSlug() + "\n";
            }
        }

        public class Rer extends Transports {
            @Override
            public String toString() {
                return "Rer " + super.getLine() + ": " + super.getSlug() + "\n";
            }
        }

        public class Tramway extends Transports {
            @Override
            public String toString() {
                return "Tramway " + super.getLine() + ": " + super.getSlug() + "\n";
            }
        }
    }
}
