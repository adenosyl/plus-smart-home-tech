package ru.yandex.practicum.analyzer.service;

import com.google.protobuf.util.Timestamps;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.analyzer.model.Action;
import ru.yandex.practicum.grpc.telemetry.event.ActionTypeProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc.HubRouterControllerBlockingStub;

@Service
@RequiredArgsConstructor
public class ActionExecutor {

    @GrpcClient("hub-router")
    private HubRouterControllerBlockingStub client;

    public void send(Action action, String hubId, String scenarioName) {

        ActionTypeProto type = mapType(action.getType());

        DeviceActionProto.Builder builder = DeviceActionProto.newBuilder()
                .setSensorId(action.getSensorId())
                .setType(type);

        if (action.getValue() != null) {
            builder.setValue(action.getValue());
        }

        DeviceActionRequest request = DeviceActionRequest.newBuilder()
                .setHubId(hubId)
                .setScenarioName(scenarioName)
                .setAction(builder.build())
                .setTimestamp(Timestamps.fromMillis(System.currentTimeMillis()))
                .build();

        client.handleDeviceAction(request);
    }

    private ActionTypeProto mapType(String type) {
        return switch (type) {
            case "ACTIVATE" -> ActionTypeProto.ACTIVATE;
            case "DEACTIVATE" -> ActionTypeProto.DEACTIVATE;
            case "INVERSE" -> ActionTypeProto.INVERSE;
            case "SET_VALUE" -> ActionTypeProto.SET_VALUE;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}