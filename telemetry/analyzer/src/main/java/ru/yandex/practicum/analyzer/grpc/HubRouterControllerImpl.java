package ru.yandex.practicum.analyzer.grpc;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import lombok.extern.slf4j.Slf4j;

import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;

@Slf4j
@GrpcService
public class HubRouterControllerImpl extends HubRouterControllerGrpc.HubRouterControllerImplBase {

    @Override
    public void handleDeviceAction(
            DeviceActionRequest request,
            StreamObserver<Empty> responseObserver
    ) {

        log.info("🔥 ACTION RECEIVED:");
        log.info("hubId: {}", request.getHubId());
        log.info("scenario: {}", request.getScenarioName());
        log.info("sensorId: {}", request.getAction().getSensorId());
        log.info("type: {}", request.getAction().getType());
        log.info("value: {}", request.getAction().getValue());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}