package at.fhv.itm3.s2.roundabout.dornbirnnorth;

import at.fhv.itm3.s2.roundabout.api.IStructureModelBuilder;
import at.fhv.itm3.s2.roundabout.api.entity.ConsumerType;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.api.entity.IRoute;
import at.fhv.itm3.s2.roundabout.entity.*;
import desmoj.core.simulator.Model;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DornbirnNorthModelBuilder implements IStructureModelBuilder {
    @Override
    public IRoundaboutStructure build(Model model) {

        IRoundaboutStructure structure = new RoundaboutStructure(model);

        // Schwefel
        StreetSection s1_in = new StreetSection(250, model, "s1_in", false);
        StreetSection s1_out = new StreetSection(20, model, "s1_out", false);

        // Achrain
        StreetSection s2_in = new StreetSection(250, model, "s2_in", false);
        StreetSection s2_out = new StreetSection(20, model, "s2_out", false);

        // Lauterach
        StreetSection s3_in = new StreetSection(250, model, "s3_in", false);
        StreetSection s3_out = new StreetSection(20, model, "s3_out", false);

        // Dornbirn Nord
        StreetSection s4_in = new StreetSection(250, model, "s4_in", false);
        StreetSection s4_out = new StreetSection(20, model, "s4_out", false);

        // Roundabout
        StreetSection s5_1 = new StreetSection(13.5, model, "s5_1", false);
        StreetSection s5_2 = new StreetSection(8.6, model, "s5_2", false);
        StreetSection s5_3 = new StreetSection(11.9, model, "s5_3", false);
        StreetSection s5_4 = new StreetSection(7.1, model, "s5_4", false);
        StreetSection s5_5 = new StreetSection(11.9, model, "s5_5", false);
        StreetSection s5_6 = new StreetSection(7.1, model, "s5_6", false);
        StreetSection s5_7 = new StreetSection(11, model, "s5_7", false);
        StreetSection s5_8 = new StreetSection(17.1, model, "s5_8", false);

        // Sources
        RoundaboutSource source1 = new RoundaboutSource(model, "so1", false, s1_in);
        RoundaboutSource source2 = new RoundaboutSource(model, "so2", false, s2_in);
        RoundaboutSource source3 = new RoundaboutSource(model, "so3", false, s3_in);
        RoundaboutSource source4 = new RoundaboutSource(model, "so4", false, s4_in);

        // Sinks
        RoundaboutSink sink1 = new RoundaboutSink(model, "sink1", false);
        RoundaboutSink sink2 = new RoundaboutSink(model, "sink2", false);
        RoundaboutSink sink3 = new RoundaboutSink(model, "sink3", false);
        RoundaboutSink sink4 = new RoundaboutSink(model, "sink4", false);

        // STREETCONNECTORS
        StreetConnector c1_1 = new StreetConnector(Collections.singletonList(s1_out), Collections.singletonList(sink1));
        StreetConnector c2_1 = new StreetConnector(Collections.singletonList(s2_out), Collections.singletonList(sink2));
        StreetConnector c3_1 = new StreetConnector(Collections.singletonList(s3_out), Collections.singletonList(sink3));
        StreetConnector c4_1 = new StreetConnector(Collections.singletonList(s4_out), Collections.singletonList(sink4));

        StreetConnector c5_1 = new StreetConnector(Arrays.asList(s5_8, s2_in), Collections.singletonList(s5_1));
        StreetConnector c5_2 = new StreetConnector(Collections.singletonList(s5_1), Arrays.asList(s5_2, s3_out));
        StreetConnector c5_3 = new StreetConnector(Arrays.asList(s5_2, s3_in), Collections.singletonList(s5_3));
        StreetConnector c5_4 = new StreetConnector(Collections.singletonList(s5_3), Arrays.asList(s5_4, s4_out));
        StreetConnector c5_5 = new StreetConnector(Arrays.asList(s5_4, s4_in), Collections.singletonList(s5_5));
        StreetConnector c5_6 = new StreetConnector(Collections.singletonList(s5_5), Arrays.asList(s5_6, s1_out));
        StreetConnector c5_7 = new StreetConnector(Arrays.asList(s5_6, s1_in), Collections.singletonList(s5_7));
        StreetConnector c5_8 = new StreetConnector(Collections.singletonList(s5_7), Arrays.asList(s5_8, s2_out));

        // Tell street sections previous/next street connectors
        s1_in.setNextStreetConnector(c5_7);
        s1_out.setPreviousStreetConnector(c5_6);
        s1_out.setNextStreetConnector(c1_1);

        s2_in.setNextStreetConnector(c5_1);
        s2_out.setPreviousStreetConnector(c5_8);
        s2_out.setNextStreetConnector(c2_1);

        s3_in.setNextStreetConnector(c5_3);
        s3_out.setPreviousStreetConnector(c5_2);
        s3_out.setNextStreetConnector(c3_1);

        s4_in.setNextStreetConnector(c5_5);
        s4_out.setPreviousStreetConnector(c5_4);
        s4_out.setNextStreetConnector(c4_1);

        s5_1.setPreviousStreetConnector(c5_1);
        s5_1.setNextStreetConnector(c5_2);
        s5_2.setPreviousStreetConnector(c5_2);
        s5_2.setNextStreetConnector(c5_3);
        s5_3.setPreviousStreetConnector(c5_3);
        s5_3.setNextStreetConnector(c5_4);
        s5_4.setPreviousStreetConnector(c5_4);
        s5_4.setNextStreetConnector(c5_5);
        s5_5.setPreviousStreetConnector(c5_5);
        s5_5.setNextStreetConnector(c5_6);
        s5_6.setPreviousStreetConnector(c5_6);
        s5_6.setNextStreetConnector(c5_7);
        s5_7.setPreviousStreetConnector(c5_7);
        s5_7.setNextStreetConnector(c5_8);
        s5_8.setPreviousStreetConnector(c5_8);
        s5_8.setNextStreetConnector(c5_1);

        // Initialize tracks
        c5_1.initializeTrack(s5_8, ConsumerType.ROUNDABOUT_SECTION, s5_1, ConsumerType.ROUNDABOUT_SECTION);
        c5_1.initializeTrack(s2_in, ConsumerType.ROUNDABOUT_INLET, s5_1, ConsumerType.ROUNDABOUT_SECTION);
        c5_2.initializeTrack(s5_1, ConsumerType.ROUNDABOUT_SECTION, s5_2, ConsumerType.ROUNDABOUT_SECTION);
        c5_2.initializeTrack(s5_2, ConsumerType.ROUNDABOUT_SECTION, s3_out, ConsumerType.ROUNDABOUT_EXIT);
        c5_3.initializeTrack(s5_2, ConsumerType.ROUNDABOUT_SECTION, s5_3, ConsumerType.ROUNDABOUT_SECTION);
        c5_3.initializeTrack(s3_in, ConsumerType.ROUNDABOUT_INLET, s5_3, ConsumerType.ROUNDABOUT_SECTION);
        c5_4.initializeTrack(s5_3, ConsumerType.ROUNDABOUT_SECTION, s5_4, ConsumerType.ROUNDABOUT_SECTION);
        c5_4.initializeTrack(s5_3, ConsumerType.ROUNDABOUT_SECTION, s4_out, ConsumerType.ROUNDABOUT_EXIT);
        c5_5.initializeTrack(s5_4, ConsumerType.ROUNDABOUT_SECTION, s5_5, ConsumerType.ROUNDABOUT_SECTION);
        c5_5.initializeTrack(s4_in, ConsumerType.ROUNDABOUT_INLET, s5_5, ConsumerType.ROUNDABOUT_SECTION);
        c5_6.initializeTrack(s5_5, ConsumerType.ROUNDABOUT_SECTION, s5_6, ConsumerType.ROUNDABOUT_SECTION);
        c5_6.initializeTrack(s5_5, ConsumerType.ROUNDABOUT_SECTION, s1_out, ConsumerType.ROUNDABOUT_EXIT);
        c5_7.initializeTrack(s5_6, ConsumerType.ROUNDABOUT_SECTION, s5_7, ConsumerType.ROUNDABOUT_SECTION);
        c5_7.initializeTrack(s1_in, ConsumerType.ROUNDABOUT_INLET, s5_7, ConsumerType.ROUNDABOUT_SECTION);
        c5_8.initializeTrack(s5_7, ConsumerType.ROUNDABOUT_SECTION, s5_8, ConsumerType.ROUNDABOUT_SECTION);
        c5_8.initializeTrack(s5_7, ConsumerType.ROUNDABOUT_SECTION, s2_out, ConsumerType.ROUNDABOUT_EXIT);

        c1_1.initializeTrack(s1_out, ConsumerType.ROUNDABOUT_EXIT, sink1, ConsumerType.STREET_SECTION);
        c2_1.initializeTrack(s2_out, ConsumerType.ROUNDABOUT_EXIT, sink2, ConsumerType.STREET_SECTION);
        c3_1.initializeTrack(s3_out, ConsumerType.ROUNDABOUT_EXIT, sink3, ConsumerType.STREET_SECTION);
        c4_1.initializeTrack(s4_out, ConsumerType.ROUNDABOUT_EXIT, sink4, ConsumerType.STREET_SECTION);

        // Generate manual routes
        // 1 to 3 & 4
        IRoute oneTo_threeRoute = new Route();
        oneTo_threeRoute.addSource(source1);
        oneTo_threeRoute.addSection(s1_in);
        oneTo_threeRoute.addSection(s5_7);
        oneTo_threeRoute.addSection(s5_8);
        oneTo_threeRoute.addSection(s5_1);
        oneTo_threeRoute.addSection(s3_out);
        oneTo_threeRoute.addSection(sink3);

        IRoute oneTo_fourRoute = new Route();
        oneTo_fourRoute.addSource(source1);
        oneTo_fourRoute.addSection(s1_in);
        oneTo_fourRoute.addSection(s5_7);
        oneTo_fourRoute.addSection(s5_8);
        oneTo_fourRoute.addSection(s5_1);
        oneTo_fourRoute.addSection(s5_2);
        oneTo_fourRoute.addSection(s5_3);
        oneTo_fourRoute.addSection(s4_out);
        oneTo_fourRoute.addSection(sink4);

        // 2 to 4 & 1
        IRoute twoTo_four = new Route();
        twoTo_four.addSource(source2);
        twoTo_four.addSection(s2_in);
        twoTo_four.addSection(s5_1);
        twoTo_four.addSection(s5_2);
        twoTo_four.addSection(s5_3);
        twoTo_four.addSection(s4_out);
        twoTo_four.addSection(sink4);

        IRoute twoTo_one = new Route();
        twoTo_one.addSource(source2);
        twoTo_one.addSection(s2_in);
        twoTo_one.addSection(s5_1);
        twoTo_one.addSection(s5_2);
        twoTo_one.addSection(s5_3);
        twoTo_one.addSection(s5_4);
        twoTo_one.addSection(s5_5);
        twoTo_one.addSection(s1_out);
        twoTo_one.addSection(sink1);

        // 3 to 1 & 2
        IRoute threeTo_one = new Route();
        threeTo_one.addSource(source3);
        threeTo_one.addSection(s3_in);
        threeTo_one.addSection(s5_3);
        threeTo_one.addSection(s5_4);
        threeTo_one.addSection(s5_5);
        threeTo_one.addSection(s1_out);
        threeTo_one.addSection(sink1);

        IRoute threeTo_two = new Route();
        threeTo_two.addSource(source3);
        threeTo_two.addSection(s3_in);
        threeTo_two.addSection(s5_3);
        threeTo_two.addSection(s5_4);
        threeTo_two.addSection(s5_5);
        threeTo_two.addSection(s5_6);
        threeTo_two.addSection(s5_7);
        threeTo_two.addSection(s2_out);
        threeTo_two.addSection(sink2);

        // 4 to 2 & 3
        IRoute fourTo_two = new Route();
        fourTo_two.addSource(source4);
        fourTo_two.addSection(s4_in);
        fourTo_two.addSection(s5_5);
        fourTo_two.addSection(s5_6);
        fourTo_two.addSection(s5_7);
        fourTo_two.addSection(s2_out);
        fourTo_two.addSection(sink2);

        IRoute fourTo_three = new Route();
        fourTo_three.addSource(source4);
        fourTo_three.addSection(s4_in);
        fourTo_three.addSection(s5_5);
        fourTo_three.addSection(s5_6);
        fourTo_three.addSection(s5_7);
        fourTo_three.addSection(s5_8);
        fourTo_three.addSection(s5_1);
        fourTo_three.addSection(s3_out);
        fourTo_three.addSection(sink3);

        // Add elements to structure
        structure.addStreet(s1_in);
        structure.addStreet(s1_out);
        structure.addStreet(s2_in);
        structure.addStreet(s2_out);
        structure.addStreet(s3_in);
        structure.addStreet(s3_out);
        structure.addStreet(s4_in);
        structure.addStreet(s4_out);
        structure.addStreet(s5_1);
        structure.addStreet(s5_2);
        structure.addStreet(s5_3);
        structure.addStreet(s5_4);
        structure.addStreet(s5_5);
        structure.addStreet(s5_6);
        structure.addStreet(s5_7);
        structure.addStreet(s5_8);

        structure.addStreetConnector(c5_1);
        structure.addStreetConnector(c5_2);
        structure.addStreetConnector(c5_3);
        structure.addStreetConnector(c5_4);
        structure.addStreetConnector(c5_5);
        structure.addStreetConnector(c5_6);
        structure.addStreetConnector(c5_7);
        structure.addStreetConnector(c5_8);

        structure.addSource(source1);
        structure.addSource(source2);
        structure.addSource(source3);
        structure.addSource(source4);

        structure.addSink(sink1);
        structure.addSink(sink2);
        structure.addSink(sink3);
        structure.addSink(sink4);

        structure.addRoute(oneTo_threeRoute);
        structure.addRoute(oneTo_fourRoute);
        structure.addRoute(twoTo_four);
        structure.addRoute(twoTo_one);
        structure.addRoute(threeTo_one);
        structure.addRoute(threeTo_two);
        structure.addRoute(fourTo_two);
        structure.addRoute(fourTo_three);
        return structure;
    }
}
