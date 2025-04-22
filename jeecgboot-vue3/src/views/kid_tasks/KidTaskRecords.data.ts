import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '用户名',
    align:"center",
    dataIndex: 'userName_dictText'
   },
   {
    title: '是否完成',
    align:"center",
    dataIndex: 'isCompleted',
    customRender:({text}) => {
       return  render.renderSwitch(text, [{text:'是',value:'Y'},{text:'否',value:'N'}])
     },
   },
   {
    title: '任务日期',
    align:"center",
    dataIndex: 'taskDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '任务完成日期',
    align:"center",
    dataIndex: 'completedDate',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '任务分数',
    align:"center",
    dataIndex: 'taskPoint'
   },
   {
    title: '任务名称',
    align:"center",
    dataIndex: 'taskName'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '用户名',
    field: 'userName',
    component: 'JSelectUser',
    componentProps:{
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入用户名!'},
          ];
     },
  },
  {
    label: '任务_id',
    field: 'taskId',
    component: 'Input',
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入任务_id!'},
          ];
     },
  },
  {
    label: '是否完成',
    field: 'isCompleted',
    defaultValue: "N",
     component: 'JSwitch',
     componentProps:{
     },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入是否完成!'},
          ];
     },
  },
  {
    label: '任务日期',
    field: 'taskDate',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入任务日期!'},
          ];
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];

// 高级查询数据
export const superQuerySchema = {
  userName: {title: '用户名',order: 0,view: 'sel_user', type: 'string',},
  isCompleted: {title: '是否完成',order: 2,view: 'switch', type: 'string',},
  taskDate: {title: '任务日期',order: 3,view: 'date', type: 'string',},
  completedDate: {title: '任务完成日期',order: 4,view: 'date', type: 'string',},
  taskPoint: {title: '任务分数',order: 5,view: 'number', type: 'number',},
  taskName: {title: '任务名称',order: 6,view: 'text', type: 'string',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}