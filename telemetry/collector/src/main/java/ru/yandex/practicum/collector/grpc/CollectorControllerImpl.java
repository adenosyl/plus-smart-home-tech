package ru.yandex.practicum.collector.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;
import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.SensorEventProto;
import com.google.protobuf.Empty;

@GrpcService
public class CollectorControllerImpl extends CollectorControllerGrpc.CollectorControllerImplBase {

    @Override
    public void collectSensorEvent(SensorEventProto request,
                                   StreamObserver<Empty> responseObserver) {

        try {
            switch (request.getPayloadCase()) {

                case TEMPERATURE_SENSOR:
                    System.out.println("Температура: " +
                            request.getTemperatureSensor().getTemperatureC());
                    break;

                case MOTION_SENSOR:
                    System.out.println("Движение: " +
                            request.getMotionSensor().getMotion());
                    break;

                case LIGHT_SENSOR:
                    System.out.println("Освещенность: " +
                            request.getLightSensor().getLuminosity());
                    break;

                case CLIMATE_SENSOR:
                    System.out.println("Влажность: " +
                            request.getClimateSensor().getHumidity());
                    break;

                case SWITCH_SENSOR:
                    System.out.println("Состояние: " +
                            request.getSwitchSensor().getState());
                    break;

                default:
                    System.out.println("Неизвестный тип: " + request.getPayloadCase());
            }

            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(
                    io.grpc.Status.INTERNAL
                            .withDescription(e.getMessage())
                            .withCause(e)
                            .asRuntimeException()
            );
        }
    }
}