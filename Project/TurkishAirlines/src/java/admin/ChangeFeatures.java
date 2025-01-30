package admin;

import models.Features;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeFeatures extends HttpServlet {

    private static final char[] TYPES = {'e', 'b', 'f'};

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Features> featuresList = (ArrayList<Features>) getServletContext().getAttribute("features");

        if (featuresList == null || featuresList.size() < 3) {
            return;
        }

        for (int i = 0; i < 3; i++) {
            updateFeatureFromRequest(featuresList.get(i), request, TYPES[i]);
        }

        configureWifiAndFood(request, featuresList);

        // Marcando que houve uma mudança nos valores
        Features.isChanged = true;

        // Redirecionando para a página de mudança de features
        response.sendRedirect("ChangeFeatures.jsp");
    }

    // Método para atualizar as informações da feature a partir do request
    public void updateFeatureFromRequest(Features feature, HttpServletRequest request, char type) {

        // Salvando os valores antigos
        saveOldValues(feature);

        // Atualizando os novos valores a partir do request
        updateFeatureParameter(feature, request, "seat_pitch_" + type, this::setSeatPitch);
        updateFeatureParameter(feature, request, "seat_width_" + type, this::setSeatWidth);
        updateFeatureParameter(feature, request, "video_" + type, this::setVideoType);
        updateFeatureParameter(feature, request, "power_" + type, this::setPowerType);
        updateFeatureParameter(feature, request, "seat_type_" + type, this::setSeatType);
        updateFeatureParameter(feature, request, "price_" + type, this::setPrice);
    }

    // Método para salvar os valores antigos da feature
    public void saveOldValues(Features feature) {
        feature.setNewSeatPitch(feature.getSeatPitch());
        feature.setNewSeatWidth(feature.getSeatWidth());
        feature.setNewVideoType(feature.getVideoType());
        feature.setNewPowerType(feature.getPowerType());
        feature.setNewSeatType(feature.getSeatType());
        feature.setNewPrice(feature.getPrice());
    }

    // Método genérico para atualizar parâmetros das features
    private void updateFeatureParameter(Features feature, HttpServletRequest request, String paramName, FeatureUpdater updater) {
        String paramValue = request.getParameter(paramName);
        if (paramValue != null && !paramValue.isEmpty()) {
            updater.update(feature, paramValue);
        }
    }

    // Interface para atualizar os atributos da feature
    private interface FeatureUpdater {
        void update(Features feature, String value);
    }

    // Métodos específicos para atualizar os atributos das features
    private void setSeatPitch(Features feature, String value) {
        feature.setSeatPitch(Double.parseDouble(value));
    }

    private void setSeatWidth(Features feature, String value) {
        feature.setSeatWidth(Double.parseDouble(value));
    }

    private void setVideoType(Features feature, String value) {
        feature.setVideoType(value);
    }

    private void setPowerType(Features feature, String value) {
        feature.setPowerType(value);
    }

    private void setSeatType(Features feature, String value) {
        feature.setSeatType(value);
    }

    private void setPrice(Features feature, String value) {
        feature.setPrice(Integer.parseInt(value));
    }

    // Método para configurar Wi-Fi e comida especial para tipos específicos
    private void configureWifiAndFood(HttpServletRequest request, ArrayList<Features> featuresList) {
        configureWifi(request, featuresList, 1, "wifi_b");
        configureWifi(request, featuresList, 2, "wifi_f");

        configureSpecialFood(request, featuresList, 2, "special_food_f");
    }

    // Método para configurar o Wi-Fi de uma feature
    private void configureWifi(HttpServletRequest request, ArrayList<Features> featuresList, int index, String paramName) {
        String wifi = request.getParameter(paramName);
        if (wifi != null && !wifi.isEmpty()) {
            featuresList.get(index).setWifi(wifi);
        }
    }

    // Método para configurar a comida especial de uma feature
    private void configureSpecialFood(HttpServletRequest request, ArrayList<Features> featuresList, int index, String paramName) {
        String specialFood = request.getParameter(paramName);
        if (specialFood != null && !specialFood.isEmpty()) {
            featuresList.get(index).setSpecialFood(specialFood);
        }
    }
}
