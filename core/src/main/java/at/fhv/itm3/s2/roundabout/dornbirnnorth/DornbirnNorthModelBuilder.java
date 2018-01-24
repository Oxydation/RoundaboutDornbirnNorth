package at.fhv.itm3.s2.roundabout.dornbirnnorth;

import at.fhv.itm3.s2.roundabout.api.IStructureModelBuilder;
import at.fhv.itm3.s2.roundabout.api.entity.IRoundaboutStructure;
import at.fhv.itm3.s2.roundabout.entity.RoundaboutSink;
import at.fhv.itm3.s2.roundabout.entity.RoundaboutStructure;
import at.fhv.itm3.s2.roundabout.entity.StreetConnector;
import at.fhv.itm3.s2.roundabout.entity.StreetSection;
import desmoj.core.simulator.Model;

import java.util.Arrays;

public class DornbirnNorthModelBuilder implements IStructureModelBuilder {
    @Override
    public IRoundaboutStructure build(Model model) {

        IRoundaboutStructure structure = new RoundaboutStructure(model);

        // NORTH-EAST
        StreetSection s1_1 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s1_2 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s1_3 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s1_4 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s1_5 = new StreetSection(10, model, "s1_t1", false);

        // Roundabout
        StreetSection s2_1 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_2 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_3 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_4 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_5 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_6 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_7 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s2_8 = new StreetSection(10, model, "s1_t1", false);

        // NORTH-WEST
        StreetSection s3_1 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s3_2 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s3_3 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s3_4 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s3_5 = new StreetSection(10, model, "s1_t1", false);

        // SOUTH-WEST
        StreetSection s4_1 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s4_2 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s4_3 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s4_4 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s4_5 = new StreetSection(10, model, "s1_t1", false);

        // SOUTH-EAST
        StreetSection s5_1 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s5_2 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s5_3 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s5_4 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s5_5 = new StreetSection(10, model, "s1_t1", false);

        // Sources
        StreetSection s1_6 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s3_6 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s4_6 = new StreetSection(10, model, "s1_t1", false);
        StreetSection s5_6 = new StreetSection(10, model, "s1_t1", false);

        // Sinks
        RoundaboutSink sink1_1 = new RoundaboutSink(model, "SK1", false);
        RoundaboutSink sink1_2 = new RoundaboutSink(model, "SK1", false);

        RoundaboutSink sink4_1 = new RoundaboutSink(model, "SK1", false);
        RoundaboutSink sink4_2 = new RoundaboutSink(model, "SK1", false);

        RoundaboutSink sink3_1 = new RoundaboutSink(model, "SK1", false);
        RoundaboutSink sink3_2 = new RoundaboutSink(model, "SK1", false);

        RoundaboutSink sink5_1 = new RoundaboutSink(model, "SK1", false);
        RoundaboutSink sink5_2 = new RoundaboutSink(model, "SK1", false);


        // STREETCONNECTORS
        StreetConnector c1_1 = new StreetConnector(Arrays.asList(s1_6), Arrays.asList(s1_1, s1_2));
        StreetConnector c1_2 = new StreetConnector(Arrays.asList(s1_1), Arrays.asList(s1_3));
        StreetConnector c1_3 = new StreetConnector(Arrays.asList(s1_3), Arrays.asList(s1_4));
        StreetConnector c1_4 = new StreetConnector(Arrays.asList(s1_4), Arrays.asList(sink1_1));
        StreetConnector c1_5 = new StreetConnector(Arrays.asList(s1_5), Arrays.asList(sink1_2));

        StreetConnector c2_1 = new StreetConnector(Arrays.asList(s1_2, s2_8), Arrays.asList(s2_5));
        StreetConnector c2_2 = new StreetConnector(Arrays.asList(s2_1), Arrays.asList(s1_5, s2_2));
        StreetConnector c2_3 = new StreetConnector(Arrays.asList(s2_2, s3_2), Arrays.asList(s2_3));
        StreetConnector c2_4 = new StreetConnector(Arrays.asList(s2_3), Arrays.asList(s3_5, s2_4));
        StreetConnector c2_5 = new StreetConnector(Arrays.asList(s2_4, s4_2), Arrays.asList(s2_5));
        StreetConnector c2_6 = new StreetConnector(Arrays.asList(s2_5), Arrays.asList(s4_5, s2_6));
        StreetConnector c2_7 = new StreetConnector(Arrays.asList(s2_6,s5_2), Arrays.asList(s2_7));
        StreetConnector c2_8 = new StreetConnector(Arrays.asList(s2_7), Arrays.asList(s5_5,s2_8));

        StreetConnector c3_1 = new StreetConnector(Arrays.asList(s3_6), Arrays.asList(s3_1,s3_2));
        StreetConnector c3_2 = new StreetConnector(Arrays.asList(s3_1), Arrays.asList(s3_3));
        StreetConnector c3_3 = new StreetConnector(Arrays.asList(s3_3), Arrays.asList(s3_4));
        StreetConnector c3_4 = new StreetConnector(Arrays.asList(s3_4), Arrays.asList(sink3_1));
        StreetConnector c3_5 = new StreetConnector(Arrays.asList(s3_5), Arrays.asList(sink3_2));

        StreetConnector c4_1 = new StreetConnector(Arrays.asList(s4_6), Arrays.asList(s4_1,s4_2));
        StreetConnector c4_2 = new StreetConnector(Arrays.asList(s4_1), Arrays.asList(s4_3));
        StreetConnector c4_3 = new StreetConnector(Arrays.asList(s4_3), Arrays.asList(s4_4));
        StreetConnector c4_4 = new StreetConnector(Arrays.asList(s4_4), Arrays.asList(sink4_1));
        StreetConnector c4_5 = new StreetConnector(Arrays.asList(s4_5), Arrays.asList(sink4_2));

        StreetConnector c5_1 = new StreetConnector(Arrays.asList(s5_6), Arrays.asList(s5_1,s5_2));
        StreetConnector c5_2 = new StreetConnector(Arrays.asList(s5_1), Arrays.asList(s5_3));
        StreetConnector c5_3 = new StreetConnector(Arrays.asList(s5_3), Arrays.asList(s5_4));
        StreetConnector c5_4 = new StreetConnector(Arrays.asList(s5_4), Arrays.asList(sink5_1));
        StreetConnector c5_5 = new StreetConnector(Arrays.asList(s5_5), Arrays.asList(sink5_2));
        return structure;
    }

}
