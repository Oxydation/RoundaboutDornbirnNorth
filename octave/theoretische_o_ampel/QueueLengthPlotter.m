file1 = csvread('queueLengths_section_schwefel_inlet#1.csv');
file2 = csvread('queueLengths_section_achrain_inlet#1.csv');
file3 = csvread('queueLengths_section_lauterach_inlet#1.csv');
file4 = csvread('queueLengths_section_a14_inlet#1.csv');

figure
plot(file1, "color", "b")
hold on
plot(file2, "color", "g")
plot(file3, "color", "r")
plot(file4, "color", "k")