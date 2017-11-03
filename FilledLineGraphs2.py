import plotly.plotly as py
import plotly.graph_objs as go
import csv
import plotly

name = ""
mode = ""
score = ""
flag = 0
throughput_list = [[[],[]], [[],[]]]
average_time_list = [[[],[]], [[],[]]]
single_shot_list = [[[],[]], [[],[]]]

with open('wolf.csv') as csvfile:
	for row in csvfile:
		string_list = row.split(",")
		name = string_list[0].split(".")[3]
		mode = string_list[1].replace("'", "").replace("\"", "")
		score = string_list[4]
		if name.find('1')!=-1 or name.find('2')!=-1:
			if name.find('1')!=-1:
				flag = 0
			else:
				flag = 1
			if mode == "thrpt":
				throughput_list[flag][0].append(name)
				throughput_list[flag][1].append(score)
			elif mode == "avgt":
				average_time_list[flag][0].append(name)
				average_time_list[flag][1].append(score)
			else:
				single_shot_list[flag][0].append(name)
				single_shot_list[flag][1].append(score)


#---1 load 1 and jdk
#---2 load 1 and personal
#---3 load 2 and jdk
#---4 load 2 and personal

###DISPLAY THROUGHPUT
layout1 = go.Layout(
	title=('Throughput' + ' Ran On Wolf' + ' With 32 Threads'),
	xaxis=dict(
		tickangle=-15
	),
	yaxis=dict(
        title='ops/sec'
	),
	barmode='group'
)
trace1 = go.Bar(
    x=throughput_list[0][0],
    y=throughput_list[0][1],
	name='JDK'
)
trace2 = go.Bar(
    x=throughput_list[1][0],
    y=throughput_list[1][1],
	name='Personal'
)

###DISPLAY AVERAGE TIME
layout2 = go.Layout(
	title=('Average Time' + ' Ran On Wolf' + ' With 32 Threads'),
	xaxis=dict(
		tickangle=-15
	),
	yaxis=dict(
        title='sec'
	),
	barmode='group'
)
trace3 = go.Bar(
    x=average_time_list[0][0],
    y=average_time_list[0][1],
	name='JDK'
)
trace4 = go.Bar(
    x=average_time_list[1][0],
    y=average_time_list[1][1],
	name='Personal'
)



###DISPLAY SINGLE SHOT
layout3 = go.Layout(
	title=('Single Shot' + ' Ran On Wolf' + ' With 32 Threads'),
	xaxis=dict(
		tickangle=-15
	),
	yaxis=dict(
        title='sec'
	),
	barmode='group'
)
trace5 = go.Bar(
    x=single_shot_list[0][0],
    y=single_shot_list[0][1],
	name='JDK'
)
trace6 = go.Bar(
    x=single_shot_list[1][0],
    y=single_shot_list[1][1],
	name='Personal'
)


data1 = [trace1, trace2]
data2 = [trace3, trace4]
data3 = [trace5, trace6]
fig1 = go.Figure(data=data1, layout=layout1)
fig2 = go.Figure(data=data2, layout=layout2)
fig3 = go.Figure(data=data3, layout=layout3)
plotly.offline.plot(fig1, filename='ThroughPut Graphs32.html', image='jpeg')
plotly.offline.plot(fig2, filename='Average Time Graphs32.html', image='jpeg')
plotly.offline.plot(fig3, filename='Single Shot Graphs32.html', image='jpeg')



name = ""
mode = ""
score = ""
flag = 0
throughput_list = [[[],[]], [[],[]]]
average_time_list = [[[],[]], [[],[]]]
single_shot_list = [[[],[]], [[],[]]]

with open('wolf.csv') as csvfile:
	for row in csvfile:
		string_list = row.split(",")
		name = string_list[0].split(".")[3]
		mode = string_list[1].replace("'", "").replace("\"", "")
		score = string_list[4]
		if name.find('3')!=-1 or name.find('4')!=-1:
			if name.find('3')!=-1:
				flag = 0
			else:
				flag = 1
			if mode == "thrpt":
				throughput_list[flag][0].append(name)
				throughput_list[flag][1].append(score)
			elif mode == "avgt":
				average_time_list[flag][0].append(name)
				average_time_list[flag][1].append(score)
			else:
				single_shot_list[flag][0].append(name)
				single_shot_list[flag][1].append(score)

#---1 load 1 and jdk
#---2 load 1 and personal
#---3 load 2 and jdk
#---4 load 2 and personal



###DISPLAY THROUGHPUT
layout1 = go.Layout(
	title=('Throughput' + ' Ran On Wolf' + ' With 16 Threads'),
	xaxis=dict(
		tickangle=-15
	),
	yaxis=dict(
        title='ops/sec'
	),
	barmode='group'
)
trace1 = go.Bar(
    x=throughput_list[0][0],
    y=throughput_list[0][1],
	name='JDK'
)
trace2 = go.Bar(
    x=throughput_list[1][0],
    y=throughput_list[1][1],
	name='Personal'
)

###DISPLAY AVERAGE TIME
layout2 = go.Layout(
	title=('Average Time' + ' Ran On Wolf' + ' With 16 Threads'),
	xaxis=dict(
		tickangle=-15
	),
	yaxis=dict(
        title='sec'
	),
	barmode='group'
)
trace3 = go.Bar(
    x=average_time_list[0][0],
    y=average_time_list[0][1],
	name='JDK'
)
trace4 = go.Bar(
    x=average_time_list[1][0],
    y=average_time_list[1][1],
	name='Personal'
)



###DISPLAY SINGLE SHOT
layout3 = go.Layout(
	title=('Single Shot' + ' Ran On Wolf' + ' With 16 Threads'),
	xaxis=dict(
		tickangle=-15
	),
	yaxis=dict(
        title='sec'
	),
	barmode='group'
)
trace5 = go.Bar(
    x=single_shot_list[0][0],
    y=single_shot_list[0][1],
	name='JDK'
)
trace6 = go.Bar(
    x=single_shot_list[1][0],
    y=single_shot_list[1][1],
	name='Personal'
)


data1 = [trace1, trace2]
data2 = [trace3, trace4]
data3 = [trace5, trace6]
fig1 = go.Figure(data=data1, layout=layout1)
fig2 = go.Figure(data=data2, layout=layout2)
fig3 = go.Figure(data=data3, layout=layout3)
plotly.offline.plot(fig1, filename='ThroughPut Graphs16.html', image='jpeg')
plotly.offline.plot(fig2, filename='Average Time Graphs16.html', image='jpeg')
plotly.offline.plot(fig3, filename='Single Shot Graphs16.html', image='jpeg')