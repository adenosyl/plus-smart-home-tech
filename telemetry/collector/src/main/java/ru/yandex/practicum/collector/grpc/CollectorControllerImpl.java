package ru.yandex.practicum.collector.grpc;

import net.devh.boot.grpc.server.service.GrpcService;
import io.grpc.stub.StreamObserver;

import lombok.RequiredArgsConstructor;

import ru.yandex.practicum.grpc.telemetry.collector.CollectorControllerGrpc;
import ru.yandex.practicum.grpc.telemetry.event.*;

import ru.yandex.practicum.collector.mapper.SensorEventMapper;
import ru.yandex.practicum.collector.mapper.HubEventMapper;

import ru.yandex.practicum.collector.producer.SensorEventProducer;
import ru.yandex.practicum.collector.producer.HubEventProducer;

import ru.yandex.practicum.kafka.telemetry.event.*;

import com.google.protobuf.Empty;

@GrpcService
@RequiredArgsConstructor
public class CollectorControllerImpl extends CollectorControllerGrpc.CollectorControllerImplBase {

    private final SensorEventMapper sensorMapper;
    private final HubEventMapper hubMapper;

    private final SensorEventProducer sensorProducer;
    private final HubEventProducer hubProducer;

    @Override
    public void collectSensorEvent(SensorEventProto request,
                                   StreamObserver<Empty> responseObserver) {

        try {
            SensorEventAvro avro = sensorMapper.toAvro(request);
            sensorProducer.send(avro);

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

    @Override
    public void collectHubEvent(HubEventProto request,
                                StreamObserver<Empty> responseObserver) {

        try {
            HubEventAvro avro = hubMapper.toAvro(request);
            hubProducer.send(avro);

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