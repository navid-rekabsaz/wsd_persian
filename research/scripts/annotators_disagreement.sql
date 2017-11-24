select count(*) *2 from 
(select distinct word, am.question_id
from answer_master am
inner join answer_detail ad on am.id = ad.answer_master_id
inner join translations t on ad.translation_id = t.id
inner join clusters c on c.id=t.cluster_id
inner join words w on w.id = c.word_id
inner join questions q on q.id = am.question_id
where am.question_id in
(select question_id from
(select am.question_id, cluster_id
from answer_master am
inner join answer_detail ad on am.id = ad.answer_master_id
inner join translations t on ad.translation_id = t.id
group by am.question_id, cluster_id) m
group by question_id
having count(*) = 1)) q
group by word
order by word