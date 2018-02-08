file1 = csvread('queueLengths_s1_in#1.csv');
file2 = csvread('queueLengths_s2_in#1.csv');
file3 = csvread('queueLengths_s3_in#1.csv');
file4 = csvread('queueLengths_s4_in#1.csv');

figure
plot(file1, "color", "b")
hold on
plot(file2, "color", "g")
plot(file3, "color", "r")
plot(file4, "color", "k")