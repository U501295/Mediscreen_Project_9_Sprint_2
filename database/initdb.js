db = new Mongo().getDB("local");

db.createCollection("patientHistory");

db.patientHistory.insertMany(
    [
{"_id":1,"firstName":"TestNone","lastName":"Test",
"notes" : [
        {"creationDate":"2022-06-17" ,"content":"Patient states that they are 'feeling terrific' Weight at or below recommended"}
]},
{"_id":2,"firstName":"TestBorderline","lastName":"Test",
"notes" : [
        {"creationDate":"2022-06-17" ,"content":"Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late"},
        {"creationDate":"2022-06-01" ,"content":"Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic"}
]},
{"_id":3,"firstName":"TestInDanger","lastName":"Test",
"notes" : [
        {"creationDate":"2022-06-17" ,"content":"Patient states that they are short term Smoker" },
        {"creationDate":"2022-06-15" ,"content":"Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high"}
]},
{"_id":4,"firstName":"TestEarlyOnset","lastName":"Test",
"notes" : [
        {"creationDate":"2022-06-17" ,"content":"Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication"},
        {"creationDate":"2022-06-16" ,"content":"Patient states that they are experiencing back pain when seated for a long time"},
        {"creationDate":"2022-06-15" ,"content":"Patient states that they are a short term Smoker Hemoglobin A1C above recommended level"},
        {"creationDate":"2022-06-14" ,"content":"Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction"}
]}
]
)