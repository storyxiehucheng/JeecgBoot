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
    title: '日期',
    align:"center",
    dataIndex: 'date',
    customRender:({text}) =>{
      text = !text ? "" : (text.length > 10 ? text.substr(0,10) : text);
      return text;
    },
   },
   {
    title: '今日积分',
    align:"center",
    dataIndex: 'todayPoints'
   },
   {
    title: '总剩余积分',
    align:"center",
    dataIndex: 'totalPoints'
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
    label: '日期',
    field: 'date',
    component: 'DatePicker',
    componentProps: {
      valueFormat: 'YYYY-MM-DD'
    },
    dynamicRules: ({model,schema}) => {
          return [
                 { required: true, message: '请输入日期!'},
          ];
     },
  },
  {
    label: '今日积分',
    field: 'todayPoints',
    defaultValue: 0,
    component: 'InputNumber',
  },
  {
    label: '总剩余积分',
    field: 'totalPoints',
    defaultValue: 0,
    component: 'InputNumber',
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
  date: {title: '日期',order: 1,view: 'date', type: 'string',},
  todayPoints: {title: '今日积分',order: 2,view: 'number', type: 'number',},
  totalPoints: {title: '总剩余积分',order: 3,view: 'number', type: 'number',},
};

/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}