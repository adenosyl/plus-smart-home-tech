package ru.yandex.practicum.collector.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.*;

import com.google.protobuf.Empty;
import com.google.protobuf.Timestamp;

@Service
public class SensorEventSender {

    @GrpcClient("collector")
    private CollectorControllerGrpc.CollectorControllerBlockingStub stub;

    @Scheduled(fixedDelay = 3000)
    public void sendEvent() {

        SensorEventProto event = SensorEventProto.newBuilder()
                .setId("sensor-1")
                .setHubId("hub-1")
                .setTimestamp(
                        Timestamp.newBuilder()
                                .setSeconds(System.currentTimeMillis() / 1000)
                                .build()
                )
                .setTemperatureSensor(
                        TemperatureSensorProto.newBuilder()
                                .setTemperatureC((int) (20 + Math.random() * 10))
                                .build()
                )
                .build();

        Empty response = stub.collectSensorEvent(event);

        System.out.println("Sent event: " + event.getId());
    }
}